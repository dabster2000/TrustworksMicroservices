package dk.trustworks.aggregatorservice.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dk.trustworks.aggregatorservice.utils.BeanUtils;
import dk.trustworks.aggregatorservice.utils.LocalDateDeserializer;
import dk.trustworks.aggregatorservice.utils.LocalDateSerializer;
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

    public Client() {
    }

    public Client(String uuid, boolean active, String contactname, LocalDate created, String name, String accountmanager) {
        this.uuid = uuid;
        this.active = active;
        this.contactname = contactname;
        this.created = created;
        this.name = name;
        this.accountmanager = accountmanager;
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
