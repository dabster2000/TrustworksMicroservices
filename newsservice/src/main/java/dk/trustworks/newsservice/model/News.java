package dk.trustworks.newsservice.model;

import dk.trustworks.utils.BeanUtils;
import io.vertx.core.json.JsonObject;

import java.time.LocalDate;
import java.util.Objects;

public class News {

    private String uuid;

    private LocalDate newsdate;

    private String description;

    private String newstype;

    private String link;

    private String sha512;

    public News() {
    }

    public News(JsonObject json)  {
        BeanUtils.populateFields(this, json);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDate getNewsdate() {
        return newsdate;
    }

    public void setNewsdate(LocalDate newsdate) {
        this.newsdate = newsdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewstype() {
        return newstype;
    }

    public void setNewstype(String newstype) {
        this.newstype = newstype;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSha512() {
        return sha512;
    }

    public void setSha512(String sha512) {
        this.sha512 = sha512;
    }

    @Override
    public String toString() {
        return "News{" +
                "uuid='" + uuid + '\'' +
                ", newsdate=" + newsdate +
                ", description='" + description + '\'' +
                ", newstype='" + newstype + '\'' +
                ", link='" + link + '\'' +
                ", sha512='" + sha512 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(uuid, news.uuid) &&
                Objects.equals(sha512, news.sha512);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, sha512);
    }
}
