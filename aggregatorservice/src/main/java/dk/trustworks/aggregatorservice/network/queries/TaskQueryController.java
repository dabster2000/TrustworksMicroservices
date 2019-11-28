package dk.trustworks.aggregatorservice.network.queries;

import dk.trustworks.aggregatorservice.repository.TaskRepository;
import io.vertx.reactivex.ext.web.RoutingContext;

import static dk.trustworks.aggregatorservice.ActionHelper.ok;


public class TaskQueryController {

    private TaskRepository taskRepository;

    public TaskQueryController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void getAll(RoutingContext rc) {
        taskRepository.getAll().subscribe(ok(rc));
    }

}
