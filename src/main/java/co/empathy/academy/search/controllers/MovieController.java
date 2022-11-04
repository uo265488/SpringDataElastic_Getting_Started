package co.empathy.academy.search.controllers;

import co.empathy.academy.search.documents.Movie;
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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    @Autowired
    private MovieService service;
    @PostMapping
    public void save(@RequestBody final Movie movie) {
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable final String id) {
        return null;
    }

    /**
     * Indexing a movie document in the movie index
     */
    @PostMapping("/index")
    public String indexMovie(@RequestBody Movie movie) {

        return service.indexMovie(movie);
    }

    @PostMapping("/bulking")
    public ResponseEntity<List<Movie>> bulkIndexing(@RequestParam("file") MultipartFile multipartFile) {

        

        return ResponseEntity.ok(new ArrayList());
    }

}
