package dk.trustworks.aggregatorservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dk.trustworks.utils.BeanUtils;
import dk.trustworks.utils.LocalDateDeserializer;
import dk.trustworks.utils.LocalDateSerializer;
import io.vertx.core.json.JsonObject;

import java.time.LocalDate;

/**
 * Created by hans on 23/06/2017.
 */

public class User {

    private String uuid;
    private String firstname;
    private String lastname;
    private String username;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;

    public User() {
    }

    public User(String uuid, String firstname, String lastname, String username, LocalDate birthday) {
        this.uuid = uuid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.birthday = birthday;
    }

    public User(JsonObject json)  {
        BeanUtils.populateFields(this, json);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
