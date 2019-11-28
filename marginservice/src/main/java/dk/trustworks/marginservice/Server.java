package dk.trustworks.marginservice;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import dk.trustworks.marginservice.network.TestHandler;
import dk.trustworks.marginservice.network.queries.ExperienceConsultantQueryController;
import dk.trustworks.marginservice.network.queries.ExperienceLevelQueryController;
import dk.trustworks.marginservice.repository.ExperienceConsultantRepository;
import dk.trustworks.marginservice.repository.ExperienceLevelRepository;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.vertx.MetricsHandler;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.reactivex.CompletableHelper;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.dropwizard.MetricsService;
import io.vertx.reactivex.ext.jdbc.JDBCClient;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.StaticHandler;
import io.vertx.reactivex.ext.web.handler.sockjs.SockJSHandler;

import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class Server extends AbstractVerticle {

    private ExperienceLevelQueryController experienceLevelQueryController;
    private ExperienceConsultantQueryController experienceConsultantQueryController;

    public Server() {
    }


    @Override
    @SuppressWarnings("unchecked")
    public void start(Future<Void> fut) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        Locale.setDefault(new Locale("da", "DK"));

        //Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
        //Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        //Configuration configuration = new Configuration("marginservice").withSampler(samplerConfiguration).withReporter(reporterConfiguration);

        MetricRegistry metricRegistry = SharedMetricRegistries.getOrCreate("exported");
        CollectorRegistry.defaultRegistry.register(new DropwizardExports(metricRegistry));


        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        ConfigRetriever retriever = ConfigRetriever.create(vertx);
        retriever.rxGetConfig()
                .doOnSuccess(config -> {
                    experienceLevelQueryController = new ExperienceLevelQueryController(new ExperienceLevelRepository(JDBCClient.createShared(vertx, config, "marginservice")));
                    experienceConsultantQueryController = new ExperienceConsultantQueryController(new ExperienceConsultantRepository(JDBCClient.createShared(vertx, config, "marginservice")), new ExperienceLevelRepository(JDBCClient.createShared(vertx, config, "marginservice")));

                    router.get("/metrics").handler(new TestHandler());

                    router.get("/experiencelevels").handler(experienceLevelQueryController::getAll);
                    router.get("/experiencelevels/:id").handler(experienceLevelQueryController::getOne);
                    router.post("/experiencelevels/:level").handler(experienceLevelQueryController::findByLevel);

                    router.get("/margin/:useruuid/:rate").handler(experienceConsultantQueryController::calculateMargin);
                })
                .flatMapCompletable(config -> {
                    return vertx
                            .createHttpServer()
                            .requestHandler(router::accept)
                            .rxListen(config.getInteger("HTTP_PORT", 5560))
                            .ignoreElement();
                })
                .subscribe(CompletableHelper.toObserver(fut));

        vertx.setPeriodic(1_000L, e -> metricRegistry.counter("testCounter").inc());
/*
        MetricsService service = MetricsService.create(vertx);

        // Allow outbound traffic to the news-feed address

        BridgeOptions options = new BridgeOptions().
                addOutboundPermitted(
                        new PermittedOptions().
                                setAddress("metrics")
                );

        router.mountSubRouter("/eventbus", SockJSHandler.create(vertx).bridge(options));

        // Serve the static resources
        router.route().handler(StaticHandler.create());

        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router).listen(8080);

        // Send a metrics events every second
        vertx.setPeriodic(1000, t -> {
            JsonObject metrics = service.getMetricsSnapshot(vertx.eventBus());
            if (metrics != null) {
                vertx.eventBus().publish("metrics", metrics);
            }
        });

        // Send some messages
        Random random = new Random();
        vertx.eventBus().consumer("whatever", msg -> {
            vertx.setTimer(10 + random.nextInt(50), id -> {
                vertx.eventBus().send("whatever", "hello");
            });
        });
        vertx.eventBus().send("whatever", "hello");
        */
 
    }

}