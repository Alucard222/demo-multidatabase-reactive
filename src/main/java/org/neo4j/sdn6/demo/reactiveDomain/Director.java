package org.neo4j.sdn6.demo.reactiveDomain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Director {

    private final String name;

    private final List<MovieDomain> movies;

    public Director(String name, List<MovieDomain> movies) {
        this.name = name;
        this.movies = new ArrayList<>(movies);
    }

    public String getName() {
        return name;
    }

    public List<MovieDomain> getMovies() {
        return Collections.unmodifiableList(movies);
    }
}

