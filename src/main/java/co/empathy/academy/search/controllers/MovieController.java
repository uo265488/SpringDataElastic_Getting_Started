package co.empathy.academy.search.controllers;

import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService service;

    @PostMapping
    public void save(@RequestBody final Movie movie) {
        service.save(movie);
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable final String id) {
        return service.findById(id);
    }

    /**
     * Indexing a movie document in the movie index
     */
    @GetMapping("/index")
    public String indexMovie(@RequestBody Movie movie) {

        return service.indexMovie(movie);
    }

}
