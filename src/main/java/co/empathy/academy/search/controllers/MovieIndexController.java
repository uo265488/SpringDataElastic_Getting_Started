package co.empathy.academy.search.controllers;

import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.services.indexing.MovieIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/index")
public class MovieIndexController {
    @Autowired
    private MovieIndexService service;

    /**
     * Indexing a movie document in the movie index
     */
    @PostMapping("/index")
    public ResponseEntity<String> indexMovie(@RequestBody Movie movie) {

        return ResponseEntity.ok(service.indexDocument(movie));
    }

    @PostMapping("createIndex")
    public ResponseEntity<String> createIndex() {
        return ResponseEntity.ok(service.createIndex());
    }

    /**
     * Mapping for indexing movies
     * @param
     * @return response entity
     */
    @PostMapping("/bulking")
    public ResponseEntity<Boolean> bulkIndexing(
            @RequestParam("file") MultipartFile titleBasics,
            @RequestParam("file2") MultipartFile ratings,
            @RequestParam("file3") MultipartFile akas,
            @RequestParam("file4") MultipartFile principals) {

        return ResponseEntity.ok(service.synchronousBulkIndexingMovies(titleBasics, ratings, akas, principals));
    }

    @PostMapping
    public ResponseEntity<Movie> save(@RequestBody final Movie movie) {
        Movie newMovie = null;//service.saveMovie(movie);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newMovie.getId()).toUri();

        return ResponseEntity.created(uri).body(newMovie);
    }

}
