package co.empathy.academy.search.controllers;

import co.empathy.academy.search.documents.ResponseModel;
import co.empathy.academy.search.services.searching.MovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class MovieSearchController {
    @Autowired
    private MovieSearchService service;

    @GetMapping("/type")
    public ResponseEntity<ResponseModel> filterQueryByType(@RequestParam String type) {
        return ResponseEntity.ok(service.filterQuery("titleType", type));
    }

    @GetMapping("/year")
    public ResponseEntity<ResponseModel> rangeQueryByYear(@RequestParam int minYear,
                                                          @RequestParam int maxYear) {
        return ResponseEntity.ok(service.rangeQuery("startYear", minYear, maxYear));
    }

    @GetMapping("/minutes")
    public ResponseEntity<ResponseModel> rangeQueryByMinutes(@RequestParam int min,
                                                          @RequestParam int max) {
        return ResponseEntity.ok(service.rangeQuery("runtimeMinutes", min, max));
    }

    @GetMapping("/score")
    public ResponseEntity<ResponseModel> rangeQueryByScore(@RequestParam int min,
                                                             @RequestParam int max) {
        return ResponseEntity.ok(service.rangeQuery("score", min, max));
    }

    @GetMapping("/genres")
    public ResponseEntity<ResponseModel> rangeQueryByGenres(@RequestParam String[] genres) {
        return ResponseEntity.ok(service.filterQuery("genres", genres));
    }

}
