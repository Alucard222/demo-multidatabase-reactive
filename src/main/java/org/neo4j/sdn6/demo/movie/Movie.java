package org.neo4j.sdn6.demo.movie;

import org.neo4j.sdn6.demo.person.PersonEntity;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import java.util.List;

@Node("Movie")
public class Movie {

    @Id
    private String title;


    @DynamicLabels
    private List<String> labels;

    @Property("tagline")
    private String tagline;

    @Property("released")
    private Integer yearOfRelease;

    @Relationship(type = "ACTED_IN", direction = Direction.INCOMING)
    private List<PersonEntity> actors;

    @Relationship(type = "SEQUEL", direction = Direction.OUTGOING)
    private Sequel sequel;

    public Movie(String title, List<String> labels, String tagline, Integer yearOfRelease) {
        this.title = title;
        this.labels = labels;
        this.tagline = tagline;
        this.yearOfRelease = yearOfRelease;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<PersonEntity> getActors() {
        return actors;
    }

    public void setActors(List<PersonEntity> actors) {
        this.actors = actors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }
}
