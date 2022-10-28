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
        service.save(movie);
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable final String id) {
        return service.findById(id);
    }

}
