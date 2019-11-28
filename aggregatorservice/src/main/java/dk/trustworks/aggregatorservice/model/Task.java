package dk.trustworks.aggregatorservice.model;

import dk.trustworks.aggregatorservice.utils.BeanUtils;
import io.vertx.core.json.JsonObject;

public class Task {
    private String uuid;
    private String name;
    private String projectuuid;

    public Task() {
    }

    public Task(String uuid, String name, String projectuuid) {
        this.uuid = uuid;
        this.name = name;
        this.projectuuid = projectuuid;
    }

    public Task(JsonObject json)  {
        BeanUtils.populateFields(this, json);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectuuid() {
        return projectuuid;
    }

    public void setProjectuuid(String projectuuid) {
        this.projectuuid = projectuuid;
    }

    @Override
    public String toString() {
        return "Task{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", projectuuid='" + projectuuid + '\'' +
                '}';
    }
}
