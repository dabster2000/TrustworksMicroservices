package dk.trustworks.aggregatorservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dk.trustworks.aggregatorservice.utils.BeanUtils;
import dk.trustworks.aggregatorservice.utils.LocalDateDeserializer;
import dk.trustworks.aggregatorservice.utils.LocalDateSerializer;
import io.vertx.core.json.JsonObject;

import java.time.LocalDate;

public class WorkItem {

    // Work
    private int id;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate registered;
    private double workduration;
    private String useruuid;
    private String workas;
    private double rate;

    private String taskuuid;
    private String projectuuid;
    private String clientuuid;

    public WorkItem() {
    }

    public WorkItem(int id, LocalDate registered, double workduration, String useruuid, String workas, double rate, String taskuuid, String projectuuid, String clientuuid) {
        this.id = id;
        this.registered = registered;
        this.workduration = workduration;
        this.useruuid = useruuid;
        this.workas = workas;
        this.rate = rate;
        this.taskuuid = taskuuid;
        this.projectuuid = projectuuid;
        this.clientuuid = clientuuid;
    }

    public WorkItem(JsonObject json)  {
        BeanUtils.populateFields(this, json);
    }

    public int getId() {
        return id;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    public double getWorkduration() {
        return workduration;
    }

    public void setWorkduration(double workduration) {
        this.workduration = workduration;
    }

    public String getUseruuid() {
        return useruuid;
    }

    public void setUseruuid(String useruuid) {
        this.useruuid = useruuid;
    }

    public String getWorkas() {
        return workas;
    }

    public void setWorkas(String workas) {
        this.workas = workas;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getTaskuuid() {
        return taskuuid;
    }

    public void setTaskuuid(String taskuuid) {
        this.taskuuid = taskuuid;
    }

    public String getProjectuuid() {
        return projectuuid;
    }

    public void setProjectuuid(String projectuuid) {
        this.projectuuid = projectuuid;
    }

    public String getClientuuid() {
        return clientuuid;
    }

    public void setClientuuid(String clientuuid) {
        this.clientuuid = clientuuid;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", registered=" + registered +
                ", workduration=" + workduration +
                ", useruuid='" + useruuid + '\'' +
                ", workas='" + workas + '\'' +
                ", rate=" + rate +
                ", taskuuid='" + taskuuid + '\'' +
                ", projectuuid='" + projectuuid + '\'' +
                ", clientuuid='" + clientuuid + '\'' +
                '}';
    }
}
