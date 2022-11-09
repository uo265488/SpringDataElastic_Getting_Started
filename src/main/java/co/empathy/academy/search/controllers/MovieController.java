package co.empathy.academy.search.controllers;

import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

        return ResponseEntity.ok(service.indexDocument(movie));
    }

    /**
     * Mapping for indexing movies
     * @param multipartFile
     * @return response entity
     */
    @PostMapping("/bulking")
    public ResponseEntity<Boolean> bulkIndexing(@RequestParam("file") MultipartFile multipartFile) {

        return ResponseEntity.ok(service.synchronousBulkIndexingMovies(multipartFile));
    }

    /**
     * Mapping for indexing ratings
     * @param multipartFile
     * @return response entity
     */
    @PostMapping("/indexRatings")
    public ResponseEntity<Boolean> bulkIndexingRatings(@RequestParam("file") MultipartFile multipartFile) {

        return ResponseEntity.ok(service.synchronousBulkIndexingRatings(multipartFile));
    }

}
