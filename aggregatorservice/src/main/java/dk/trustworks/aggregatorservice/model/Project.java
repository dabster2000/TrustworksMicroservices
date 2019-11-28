package dk.trustworks.aggregatorservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dk.trustworks.utils.BeanUtils;
import dk.trustworks.utils.LocalDateDeserializer;
import dk.trustworks.utils.LocalDateSerializer;
import io.vertx.core.json.JsonObject;

import java.time.LocalDate;

public class Project {

    private String uuid;
    private Double budget;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate created;
    private String customerreference;
    private String name;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startdate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate enddate;
    private String clientuuid;
    private String userowneruuid;

    public Project() {
    }

    public Project(String uuid, Double budget, LocalDate created, String customerreference, String name, LocalDate startdate, LocalDate enddate, String clientuuid, String userowneruuid) {
        this.uuid = uuid;
        this.budget = budget;
        this.created = created;
        this.customerreference = customerreference;
        this.name = name;
        this.startdate = startdate;
        this.enddate = enddate;
        this.clientuuid = clientuuid;
        this.userowneruuid = userowneruuid;
    }

    public Project(JsonObject json)  {
        BeanUtils.populateFields(this, json);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getCustomerreference() {
        return customerreference;
    }

    public void setCustomerreference(String customerreference) {
        this.customerreference = customerreference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public String getClientuuid() {
        return clientuuid;
    }

    public void setClientuuid(String clientuuid) {
        this.clientuuid = clientuuid;
    }

    public String getUserowneruuid() {
        return userowneruuid;
    }

    public void setUserowneruuid(String userowneruuid) {
        this.userowneruuid = userowneruuid;
    }

    @Override
    public String toString() {
        return "Project{" +
                "uuid='" + uuid + '\'' +
                ", budget=" + budget +
                ", created=" + created +
                ", customerreference='" + customerreference + '\'' +
                ", name='" + name + '\'' +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", clientuuid='" + clientuuid + '\'' +
                ", userowneruuid='" + userowneruuid + '\'' +
                '}';
    }
}
