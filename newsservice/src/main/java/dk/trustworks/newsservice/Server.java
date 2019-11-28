package dk.trustworks.newsservice;

import dk.trustworks.newsservice.network.queries.NewsQueryController;
import io.vertx.core.Future;
import io.vertx.reactivex.CompletableHelper;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;

import java.util.Locale;
import java.util.TimeZone;

public class Server extends AbstractVerticle {

    private NewsQueryController newsQueryController;

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


        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        ConfigRetriever retriever = ConfigRetriever.create(vertx);
        retriever.rxGetConfig()
                .doOnSuccess(config -> {

                })
                .flatMapCompletable(config -> {
                    return vertx
                            .createHttpServer()
                            .requestHandler(router::accept)
                            .rxListen(config.getInteger("HTTP_PORT", 5760))
                            .ignoreElement();
                })
                .subscribe(CompletableHelper.toObserver(fut));

    }

}