package dk.trustworks.aggregatorservice;

import dk.trustworks.aggregatorservice.network.queries.*;
import dk.trustworks.aggregatorservice.repository.*;
import io.jaegertracing.Configuration;
import io.opentracing.util.GlobalTracer;
import io.reactivex.Completable;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.shiro.ShiroAuthOptions;
import io.vertx.reactivex.CompletableHelper;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.auth.AuthProvider;
import io.vertx.reactivex.ext.auth.shiro.ShiroAuth;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.AuthHandler;
import io.vertx.reactivex.ext.web.handler.BasicAuthHandler;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.SessionHandler;
import io.vertx.reactivex.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.auth.shiro.ShiroAuthRealmType;

import java.util.Locale;
import java.util.TimeZone;

public class Server extends AbstractVerticle {

    private WorkItemsQueryController workItemsQueryController;
    private UserQueryController userQueryController;
    private TaskQueryController taskQueryController;
    private ProjectQueryController projectQueryController;
    private ClientQueryController clientQueryController;

    public Server() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Future<Void> fut) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        Locale.setDefault(new Locale("da", "DK"));

        Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration configuration = new Configuration("aggregaterservice").withSampler(samplerConfiguration).withReporter(reporterConfiguration);

        GlobalTracer.registerIfAbsent(configuration.getTracer());

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        ConfigRetriever retriever = ConfigRetriever.create(vertx);
        retriever.rxGetConfig()
                .doOnSuccess(config -> {
                    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

                    workItemsQueryController = new WorkItemsQueryController(new WorkItemsRepository(JDBCClient.createShared(vertx, config, "aggregaterservice")));
                    userQueryController = new UserQueryController(new UserRepository(JDBCClient.createShared(vertx, config, "aggregaterservice")));
                    taskQueryController = new TaskQueryController(new TaskRepository(JDBCClient.createShared(vertx, config, "aggregaterservice")));
                    projectQueryController = new ProjectQueryController(new ProjectRepository(JDBCClient.createShared(vertx, config, "aggregaterservice")));
                    clientQueryController = new ClientQueryController(new ClientRepository(JDBCClient.createShared(vertx, config, "aggregaterservice")));

                    AuthProvider authProvider = ShiroAuth.create(
                            vertx,  new ShiroAuthOptions().setType(ShiroAuthRealmType.PROPERTIES).setConfig(new JsonObject()));
                    AuthHandler basicAuthHandler = BasicAuthHandler.create(authProvider);
                    router.route("/*").handler(basicAuthHandler);

                    router.get("/workitems").handler(workItemsQueryController::getAll);
                    router.get("/clients").handler(clientQueryController::getAll);
                    router.get("/projects").handler(projectQueryController::getAll);
                    router.get("/tasks").handler(taskQueryController::getAll);
                    router.get("/users").handler(userQueryController::getAll);

                })
                .flatMapCompletable(config -> createHttpServer(config, router))
                .subscribe(CompletableHelper.toObserver(fut));
    }

    private Completable createHttpServer(JsonObject config, Router router) {
        return vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .rxListen(config.getInteger("HTTP_PORT", 5660))
                .ignoreElement();
    }
}