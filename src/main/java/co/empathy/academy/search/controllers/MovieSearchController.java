package co.empathy.academy.search.controllers;

import co.empathy.academy.search.documents.ResponseModel;
import co.empathy.academy.search.services.searching.MovieSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class MovieSearchController {
    @Autowired
    private MovieSearchService service;

    @GetMapping("/type")
    public ResponseEntity<ResponseModel> filterQueryByType(
            @RequestParam String type,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue =  "") String sortOrder,
            @RequestParam(defaultValue =  "") String orderBy) {

        if(type == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(
                service.filterQuery("titleType", type, size, sortOrder, orderBy));
    }
    @GetMapping("/genres")
    public ResponseEntity<ResponseModel> filterQueryByGenres(
            @RequestParam String[] genres,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue =  "") String sortOrder,
            @RequestParam(defaultValue =  "") String orderBy) {

        if(genres == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(
                service.filterQuery("genres", genres, size, sortOrder, orderBy));
    }


    @GetMapping("/{fieldName}")
    public ResponseEntity<ResponseModel> rangeQueryByYear(@PathVariable String fieldName,
                                                          @RequestParam int minYear,
                                                          @RequestParam int maxYear,
                                                          @RequestParam(defaultValue = "100") int size,
                                                          @RequestParam(defaultValue =  "") String sortOrder,
                                                          @RequestParam(defaultValue = "") String orderBy) {

        if(fieldName == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(fieldName != "minutes" && fieldName != "score" && fieldName != "year")
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(
                service.rangeQuery(fieldName, minYear, maxYear, size, sortOrder, orderBy));
    }
}
