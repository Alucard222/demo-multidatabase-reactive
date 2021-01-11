package org.neo4j.sdn6.demo.controller;

import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.TypeSystem;
import org.neo4j.sdn6.demo.reactiveDomain.Director;
import org.neo4j.sdn6.demo.reactiveDomain.MovieDomain;
import org.springframework.data.neo4j.core.ReactiveNeo4jClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/movies/reactiveClient")
public class MovieReactiveClientController {

    private final ReactiveNeo4jClient client;

    public MovieReactiveClientController(ReactiveNeo4jClient client) {
        this.client = client;
    }

    @GetMapping("/count")
    Mono<Long> getMovieCount() {
        return client
                .query("MATCH (m:Movie) RETURN count(m)")
                .in("neo4j")
                .fetchAs(Long.class)
                .mappedBy((ts, r) -> r.get(0).asLong())
                .one();

    }
    @GetMapping("/{director}")
    Mono<Director> getDirectorMoviesByName(@PathVariable String director){
        return client
                .query(""
                        + " MATCH (p:Person {name: $name})-[:DIRECTED]->(m:Movie)"
                        + " RETURN p.name as name, collect(m) as movies")
                .bind(director).to("name")
                .fetchAs(Director.class).mappedBy((TypeSystem t, Record record) -> {
                    List<MovieDomain> movies = record.get("movies")
                            .asList(v -> new MovieDomain((v.get("title").asString()), Collections.emptyList()));
                    return new Director(record.get("name").asString(), movies);
                })
                .one();
    }

    @GetMapping("/all")
    Flux<Director> getAllDirectorMovies(){
        return client
                .query(""
                        + " MATCH (p:Person)-[:DIRECTED]->(m:Movie)"
                        + " RETURN p.name as name, collect(m) as movies, labels(m) as labels")
                .fetchAs(Director.class).mappedBy((TypeSystem t, Record record) -> {
                    List<MovieDomain> movies = record.get("movies")
                            .asList(v -> new MovieDomain((v.get("title").asString()), record.get("labels").asList(Value::asString)));
                    return new Director(record.get("name").asString(), movies);
                })
                .all();
    }
}
