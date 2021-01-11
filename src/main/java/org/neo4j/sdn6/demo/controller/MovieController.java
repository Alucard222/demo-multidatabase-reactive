package org.neo4j.sdn6.demo.controller;

import org.neo4j.sdn6.demo.movie.Movie;
import org.neo4j.sdn6.demo.movie.MovieDTOProjection;
import org.neo4j.sdn6.demo.movie.MovieProjection;
import org.neo4j.sdn6.demo.movie.MovieRepository;
import org.springframework.data.neo4j.core.ReactiveNeo4jClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final ReactiveNeo4jClient client;

    public MovieController(MovieRepository movieRepository, ReactiveNeo4jClient client) {
        this.movieRepository = movieRepository;
        this.client = client;
    }

    @GetMapping("/{actorName}")
    Flux<Movie> getActorMovies(@PathVariable String actorName){
        return movieRepository.findMoviesByActorName(actorName);
    }

    @GetMapping("/total/actors")
    Flux<MovieDTOProjection> getActorMovies(){
        return movieRepository.findMoviesWithTotalActors();
    }

    @GetMapping("/titles/{actorName}")
    Flux<MovieProjection> getActorMovieTitles(@PathVariable String actorName){
        return movieRepository.findMovieTitlesByActorName(actorName);
    }

    @GetMapping("/all")
    Flux<Movie> getTotalMovies(){
        return movieRepository.findTotalMovies();
    }

    @GetMapping("/sequels/path/{title}")
    Flux<Movie> getMovieSequelPath(@PathVariable String title){
        return movieRepository.findSequelMoviesPath(title);
    }

    @GetMapping("/sequels/{title}")
    Flux<Movie> getMovieSequel(@PathVariable String title){
        return movieRepository.findSequelMovies(title);
    }
}
