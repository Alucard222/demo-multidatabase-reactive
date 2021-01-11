package org.neo4j.sdn6.demo.movie;

import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class Sequel {

    @TargetNode
    private final Movie movie;

    public Sequel(Movie movie) {
        this.movie = movie;
    }
}
