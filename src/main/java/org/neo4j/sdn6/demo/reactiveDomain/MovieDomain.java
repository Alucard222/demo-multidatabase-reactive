package org.neo4j.sdn6.demo.reactiveDomain;

import java.util.List;

public class MovieDomain {

    private final String title;
    private final List<String> labels;

    public MovieDomain(String title, List<String> labels) {
        this.title = title;
        this.labels = labels;
    }

    public List<String> getLabels() {
        return labels;
    }

    public String getTitle() {
        return title;
    }
}