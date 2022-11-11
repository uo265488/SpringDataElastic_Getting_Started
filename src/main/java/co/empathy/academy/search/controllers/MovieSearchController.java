package co.empathy.academy.search.controllers;

import co.elastic.clients.elasticsearch.core.search.Hit;
import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.services.MovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class MovieSearchController {
    @Autowired
    private MovieSearchService service;

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Hit<Movie>>> filterQueryByType(@PathVariable("type") String type) {
        return ResponseEntity.ok(service.filterQuery("type", type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable final String id) {
        Movie movie = null; // repository.findMovieById(id);

        return movie == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(movie);
    }
}
