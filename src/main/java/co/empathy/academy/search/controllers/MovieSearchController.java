package co.empathy.academy.search.controllers;

import co.empathy.academy.search.documents.FieldAttr;
import co.empathy.academy.search.documents.ResponseModel;
import co.empathy.academy.search.services.searching.MovieSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("")
public class MovieSearchController {
    @Autowired
    private MovieSearchService service;

    @Operation(summary = "Filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success. "),
            @ApiResponse(responseCode = "400", description = "Bad request. ")
    })
    @Parameters(value = {
            @Parameter(name = "type", description = "Value of the field. "),
            @Parameter(name = "genres", description = "List of values of the field. "),
            @Parameter(name = "minScore", description = "Min value of the range. "),
            @Parameter(name = "maxScore", description = "Max value of the range. "),
            @Parameter(name = "size", description = "Number of documents in the response. Default = 100 "),
            @Parameter(name = "sortOrder", description = "ASC or DESC"),
            @Parameter(name = "sortRating", description = "Sort rating ASC or DESC"),
            @Parameter(name = "sortBy", description = "condition to sort"),
    })
    @GetMapping("/search")
    public ResponseEntity<ResponseModel> filterQueryByType(
            @RequestParam Optional<String> type,
            @RequestParam Optional<String> genres,
            @RequestParam(defaultValue = "0.0") double minScore,
            @RequestParam(defaultValue = "10.0") double maxScore,
            @RequestParam(defaultValue = "0") int minYear,
            @RequestParam Optional<Integer> maxYear,
            @RequestParam (defaultValue = "0") int minMinutes,
            @RequestParam Optional<Integer> maxMinutes,
            @RequestParam(defaultValue = "100") int maxNHits,
            @RequestParam Optional<String> sortRating,
            @RequestParam Optional<String> sortOrder,
            @RequestParam Optional<String> sortBy) {


        if(incorrectSortParams(sortRating, sortOrder, sortBy)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(service.filterQuery(type, genres, minScore, maxScore, minYear, maxYear,
                minMinutes, maxMinutes, maxNHits, sortOrder, sortBy, sortRating));
    }

    private boolean incorrectSortParams(Optional<String> sortRating, Optional<String> sortOrder, Optional<String> sortBy) {
        if(sortRating.isPresent() && (!sortRating.get().equalsIgnoreCase("asc")
        && !sortRating.get().equalsIgnoreCase("desc"))) return true;
        if(sortOrder.isEmpty() && sortBy.isPresent()) return true;
        if(sortOrder.isPresent() && sortBy.isEmpty()) return true;
        if(sortOrder.isPresent() && sortBy.isPresent() &&
                ((!sortOrder.get().equalsIgnoreCase("asc")
                        && !sortOrder.get().equalsIgnoreCase("desc"))
                || !FieldAttr.Movie.isField(sortBy.get()))) return true;

        return false;
    }



}
