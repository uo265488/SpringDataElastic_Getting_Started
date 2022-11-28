package co.empathy.academy.search.controllers;

import co.empathy.academy.search.documents.FieldAttr;
import co.empathy.academy.search.documents.ResponseModel;
import co.empathy.academy.search.services.searching.MovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/search")
public class MovieSearchController {
    @Autowired
    private MovieSearchService service;

    @GetMapping("/type")
    public ResponseEntity<ResponseModel> filterQueryByType(
            @RequestParam String type,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam Optional<String> sortOrder,
            @RequestParam Optional<String> orderBy) {

        if(type == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(incorrectSortParams(sortOrder, orderBy)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(
                service.filterQuery("titleType", type, size,
                        sortOrder.isPresent() ? sortOrder.get() : "",
                        orderBy.isPresent() ? orderBy.get() : ""));
    }

    private boolean incorrectSortParams(Optional<String> sortOrder, Optional<String> orderBy) {
        if(sortOrder.isEmpty() && orderBy.isPresent()) return true;
        if(sortOrder.isPresent() && orderBy.isEmpty()) return true;
        if(sortOrder.isPresent() && orderBy.isPresent() &&
                ((!sortOrder.get().equalsIgnoreCase("asc")
                        && !sortOrder.get().equalsIgnoreCase("desc"))
                || !FieldAttr.Movie.isField(orderBy.get()))) return true;

        return false;
    }

    @GetMapping("/genres")
    public ResponseEntity<ResponseModel> filterQueryByGenres(
            @RequestParam String[] genres,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam Optional<String> sortOrder,
            @RequestParam Optional<String> orderBy) {

        if(genres == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(incorrectSortParams(sortOrder, orderBy)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(
                service.filterQuery("genres", genres, size,
                        sortOrder.isPresent() ? sortOrder.get() : "",
                        orderBy.isPresent() ? orderBy.get() : ""));
    }


    @GetMapping("/score")
    public ResponseEntity<ResponseModel> rangeQueryByScore(@RequestParam int min,
                                                          @RequestParam int max,
                                                          @RequestParam(defaultValue = "100") int size,
                                                          @RequestParam(defaultValue = "") Optional<String> sortOrder,
                                                          @RequestParam(defaultValue = "") Optional<String> orderBy) {

        if(incorrectSortParams(sortOrder, orderBy)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(
                service.rangeQuery("score", min, max, size, sortOrder.get(), orderBy.get()));
    }

    @GetMapping("/year")
    public ResponseEntity<ResponseModel> rangeQueryByYear(@RequestParam int min,
                                                          @RequestParam int max,
                                                          @RequestParam(defaultValue = "100") int size,
                                                          @RequestParam(defaultValue = "") Optional<String> sortOrder,
                                                          @RequestParam(defaultValue = "") Optional<String> orderBy) {

        if(incorrectSortParams(sortOrder, orderBy)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(
                service.rangeQuery("startYear", min, max, size, sortOrder.get(), orderBy.get()));
    }

    @GetMapping("/minutes")
    public ResponseEntity<ResponseModel> rangeQueryByMinutes(@RequestParam int min,
                                                          @RequestParam int max,
                                                          @RequestParam(defaultValue = "100") int size,
                                                          @RequestParam(defaultValue = "") Optional<String> sortOrder,
                                                          @RequestParam(defaultValue = "") Optional<String> orderBy) {

        if(incorrectSortParams(sortOrder, orderBy)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(
                service.rangeQuery("runtimeMinutes", min, max, size, sortOrder.get(), orderBy.get()));
    }

}
