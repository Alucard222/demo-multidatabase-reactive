package org.neo4j.sdn6.demo.movie;

import org.neo4j.sdn6.demo.person.PersonEntity;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

public class MovieDTOProjection {

    private String title;
    private Integer yearOfRelease;
    private Long numberOfActors;

    @Relationship(type = "ACTED_IN", direction = Relationship.Direction.INCOMING)
    private List<PersonEntity> actors;

    public MovieDTOProjection(String title, Integer yearOfRelease, List<PersonEntity> actors, Long numberOfActors) {
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        this.actors = actors;
        this.numberOfActors = numberOfActors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Long getNumberOfActors() {
        return numberOfActors;
    }

    public void setNumberOfActors(Long numberOfActors) {
        this.numberOfActors = numberOfActors;
    }

    public List<PersonEntity> getActors() {
        return actors;
    }

    public void setActors(List<PersonEntity> actors) {
        this.actors = actors;
    }
}
