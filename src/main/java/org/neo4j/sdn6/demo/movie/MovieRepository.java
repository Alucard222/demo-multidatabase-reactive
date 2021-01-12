package org.neo4j.sdn6.demo.movie;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Flux;

public interface MovieRepository extends ReactiveNeo4jRepository<Movie, String> {

    @Query("MATCH (m:Movie)<-[r:ACTED_IN]-(p:Person) WHERE p.name = $actorName RETURN m")
    Flux<Movie> findMoviesByActorName(String actorName);

    @Query("MATCH (m:Movie)<-[:ACTED_IN]-(p:Person) WHERE p.name = $actorName RETURN m")
    Flux<MovieProjection> findMovieTitlesByActorName(String actorName);

    @Query("MATCH (m:Movie)<-[r:ACTED_IN]-(p:Person) RETURN m, COLLECT(r), COLLECT(p), COUNT(r) as numberOfActors")
    Flux<MovieDTOProjection> findMoviesWithTotalActors();

    @Query("MATCH (m:Movie)<-[r:ACTED_IN]-(p:Person) RETURN m, COLLECT(r), COLLECT(p), labels(m) as __nodeLabels__")
    Flux<Movie> findTotalMovies();

    @Query("MATCH path=(m:Movie)-[:SEQUEL*]->(s:Movie) WHERE m.title = $title" +
            " WITH path, nodes(path) as movieList, relationships(path) as r" +
            " UNWIND movieList as movie" +
            " MATCH (movie)<-[ra:ACTED_IN]-(p:Person) " +
            "RETURN movie, COLLECT(r), COLLECT(ra), COLLECT(DISTINCT p)")
    Flux<Movie> findSequelMoviesPath(String title);

}
