package dk.trustworks.newsservice.network.queries;

import dk.trustworks.newsservice.repository.NewsRepository;
import io.vertx.reactivex.ext.web.RoutingContext;

import static dk.trustworks.utils.ActionHelper.ok;


public class NewsQueryController {

    private NewsRepository newsRepository;

    public NewsQueryController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void getAll(RoutingContext rc) {
        newsRepository.getAll().subscribe(ok(rc));
    }

}
