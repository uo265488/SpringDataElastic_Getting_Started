package co.empathy.academy.search.controllers;

import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
