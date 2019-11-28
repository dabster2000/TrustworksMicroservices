package dk.trustworks.aggregatorservice.network.queries;

import dk.trustworks.aggregatorservice.repository.ProjectRepository;
import io.vertx.reactivex.ext.web.RoutingContext;

import static dk.trustworks.utils.ActionHelper.ok;


public class ProjectQueryController {

    private ProjectRepository projectRepository;

    public ProjectQueryController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void getAll(RoutingContext rc) {
        projectRepository.getAll().subscribe(ok(rc));
    }

}
