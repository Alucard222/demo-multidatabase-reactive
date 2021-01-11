package org.neo4j.sdn6.demo.person;

import org.neo4j.sdn6.demo.movie.Movie;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class ReviewRelationship {

    private String summary;

    private Integer rating;

    @TargetNode
    private Movie movie;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Movie getMovieEntity() {
        return movie;
    }

    public void setMovieEntity(Movie movie) {
        this.movie = movie;
    }
}
