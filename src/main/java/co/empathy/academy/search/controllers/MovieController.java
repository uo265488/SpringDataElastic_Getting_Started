package co.empathy.academy.search.controllers;

import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.parser.MultiPartToMovieListParser;
import co.empathy.academy.search.services.MovieService;
import jakarta.ws.rs.core.HttpHeaders;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    @Autowired
    private MovieService service;
    @PostMapping
    public ResponseEntity<Movie> save(@RequestBody final Movie movie) {
        Movie newMovie = service.saveMovie(movie);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newMovie.getId()).toUri();

        return ResponseEntity.created(uri).body(newMovie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable final String id) {
        Movie movie = service.findMovieById(id);

        return movie == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(movie);
    }

    /**
     * Indexing a movie document in the movie index
     */
    @PostMapping("/index")
    public ResponseEntity<String> indexMovie(@RequestBody Movie movie) {

        return ResponseEntity.ok(service.indexMovie(movie));
    }

    @PostMapping("/bulking")
    public ResponseEntity<List<Movie>> bulkIndexing(@RequestParam("file") MultipartFile multipartFile) {

        List<Movie> movieList = new MultiPartToMovieListParser().toMovieList(multipartFile);

        return ResponseEntity.ok(movieList);
    }

}
