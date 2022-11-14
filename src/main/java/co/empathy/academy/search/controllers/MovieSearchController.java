package co.empathy.academy.search.controllers;

import co.elastic.clients.elasticsearch.core.search.Hit;
import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.documents.ResponseModel;
import co.empathy.academy.search.services.MovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class MovieSearchController {
    @Autowired
    private MovieSearchService service;

    @GetMapping("/type/{type}")
    public ResponseEntity<ResponseModel> filterQueryByType(@PathVariable("type") String type) {
        return ResponseEntity.ok(service.filterQuery("titleType", type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable final String id) {
        Movie movie = null; // repository.findMovieById(id);

        return movie == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(movie);
    }

    @GetMapping("/year")
    public ResponseEntity<ResponseModel> rangeQueryByYear(@RequestParam int minYear,
                                                          @RequestParam int maxYear) {
        return ResponseEntity.ok(service.rangeQuery("startYear", minYear, maxYear));
    }
}
