package org.neo4j.sdn6.demo.controller;

import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.TypeSystem;
import org.neo4j.sdn6.demo.person.PersonEntity;
import org.neo4j.sdn6.demo.reactiveDomain.Director;
import org.neo4j.sdn6.demo.reactiveDomain.MovieDomain;
import org.springframework.data.neo4j.core.ReactiveNeo4jClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

    @PutMapping("/update/{title}/{tagline}")
    Mono<MovieDomain> updateTagline(@PathVariable String title, @PathVariable String tagline){
        return client
                .query(""
                        + "MATCH (movie:Movie {title: $title})"
                        + "SET movie.tagline = $tagline "
                        + "RETURN movie.title AS title, labels(movie) as labels ")
                .bind(title).to("title")
                .bind(tagline).to("tagline")
                .fetchAs(MovieDomain.class).mappedBy((TypeSystem t, Record record) -> {
                    return new MovieDomain(record.get("title").asString(), record.get("labels").asList(Value::asString));
                })
                .one();
    }

    @PostMapping("/delete/{title}")
    Mono<MovieDomain> deleteMovie(@PathVariable String title){
        return client
                .query(
                        "MATCH (movie:Movie {title: $title})"
                        + " WITH movie, movie.title as title, labels(movie) as labels"
                        + " DETACH DELETE movie RETURN title, labels")
                .bind(title).to("title")
                .fetchAs(MovieDomain.class).mappedBy((TypeSystem t, Record record) -> {
                    MovieDomain movieDomain = new MovieDomain(record.get("title").asString(), record.get("labels").asList(Value::asString));
                    return movieDomain;
                })
                .one();
    }

    @PutMapping("/merge/{title}/{tagline}/{released}")
    Mono<MovieDomain>mergeTitle(@PathVariable String title, @PathVariable String tagline , @PathVariable int released ){
        return client
                .query(
                        "MERGE (movie:Movie {title: $title})"
                        + " ON CREATE SET "
                                + " movie.title = $title, movie.tagline = $tagline, movie.released= $released"
                        + " ON MATCH SET movie.tagline = $tagline"
                        + " RETURN movie.title as newTitle, labels(movie) as labels")
                .bind(title).to("title")
                .bind(tagline).to("tagline")
                .bind(released).to("released")
                .fetchAs(MovieDomain.class).mappedBy((TypeSystem t, Record record) -> {
                    MovieDomain ret = new MovieDomain(record.get("newTitle").asString(), record.get("labels").asList(Value::asString));
                    return ret;
                })
                .one();
    }

    @PostMapping("/merge/{title}")
    Flux<PersonEntity> merge(@PathVariable String title, @RequestBody List <PersonEntity> input){

        List<HashMap> people = new ArrayList<>();
        for (int i=0;i<input.size();i++){
            System.out.println(input.get(i));
            HashMap hash = new HashMap();
            hash.put("id", input.get(i).getId());
            hash.put("name", input.get(i).getName());
            hash.put("born", input.get(i).getBorn());
            people.add(hash);
        }

        return client
                .query(
                        "MATCH (movie:Movie {title: $title})"
                                + " WITH movie "
                       + "UNWIND $people as person"
                                + " MERGE (p:Person {name: person.name})"
                                + " ON CREATE SET p.name = person.name, p.born = person.born"
                                + " MERGE (p)-[r:ACTED_IN]->(movie) "
                                + " RETURN p.name as name, p.born as born, ID(p) as id")
                .bind(title).to("title")
                .bind(people).to("people")
                .fetchAs(PersonEntity.class).mappedBy((TypeSystem t, Record record) -> {
                    PersonEntity person = new PersonEntity(record.get("id").asLong(), record.get("name").asString(), record.get("born").asInt());
                    return person;
                })
                .all();
    }
}



