package dk.trustworks.aggregatorservice.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dk.trustworks.utils.BeanUtils;
import dk.trustworks.utils.LocalDateDeserializer;
import dk.trustworks.utils.LocalDateSerializer;
import io.vertx.core.json.JsonObject;

import java.time.LocalDate;

public class Client {
    private String uuid;
    private boolean active;
    private String contactname;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate created;
    private String name;
    private String accountmanager;
    private String crmid;

    public Client() {
    }

    public Client(JsonObject json)  {
        BeanUtils.populateFields(this, json);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountmanager() {
        return accountmanager;
    }

    public void setAccountmanager(String accountmanager) {
        this.accountmanager = accountmanager;
    }

    public String getCrmid() {
        return crmid;
    }

    public void setCrmid(String crmid) {
        this.crmid = crmid;
    }

    @Override
    public String toString() {
        return "Client{" +
                "uuid='" + uuid + '\'' +
                ", active=" + active +
                ", contactname='" + contactname + '\'' +
                ", created=" + created +
                ", name='" + name + '\'' +
                ", accountmanager='" + accountmanager + '\'' +
                '}';
    }
}
