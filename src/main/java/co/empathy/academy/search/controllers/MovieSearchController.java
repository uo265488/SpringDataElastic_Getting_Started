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
@RequestMapping("/search")
public class MovieSearchController {
    @Autowired
    private MovieSearchService service;

    @Operation(summary = "Querying by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success. "),
            @ApiResponse(responseCode = "400", description = "Bad request. ")
    })
    @Parameters(value = {
            @Parameter(name = "type", required = true, description = "Value of the field. "),
            @Parameter(name = "size", description = "Number of documents in the response. Default = 100 "),
            @Parameter(name = "sortOrder", description = "ASC or DESC"),
            @Parameter(name = "sortBy", description = "condition to sort"),
    })
    @GetMapping("/type")
    public ResponseEntity<ResponseModel> filterQueryByType(
            @RequestParam String type,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam Optional<String> sortOrder,
            @RequestParam Optional<String> sortBy) {

        if(type == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(incorrectSortParams(sortOrder, sortBy)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(
                service.filterQuery("titleType", type, size,
                        sortOrder.isPresent() ? sortOrder.get() : "",
                        sortBy.isPresent() ? sortBy.get() : ""));
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

    @Operation(summary = "Querying by genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success. "),
            @ApiResponse(responseCode = "400", description = "Bad request. ")
    })
    @Parameters(value = {
            @Parameter(name = "genres", required = true, description = "List of values of the field. "),
            @Parameter(name = "size", description = "Number of documents in the response. Default = 100 "),
            @Parameter(name = "sortOrder", description = "ASC or DESC"),
            @Parameter(name = "sortBy", description = "condition to sort"),
    })
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

    @Operation(summary = "Range query by score")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success. "),
            @ApiResponse(responseCode = "400", description = "Bad request. ")
    })
    @Parameters(value = {
            @Parameter(name = "min", required = true, description = "Min value of the range. "),
            @Parameter(name = "max", required = true, description = "Max value of the range. "),
            @Parameter(name = "size", description = "Number of documents in the response. Default = 100 "),
            @Parameter(name = "sortOrder", description = "ASC or DESC"),
            @Parameter(name = "sortBy", description = "condition to sort"),
    })
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

    @Operation(summary = "Range query by year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success. "),
            @ApiResponse(responseCode = "400", description = "Bad request. ")
    })
    @Parameters(value = {
            @Parameter(name = "min", required = true, description = "Min value of the range. "),
            @Parameter(name = "max", required = true, description = "Max value of the range. "),
            @Parameter(name = "size", description = "Number of documents in the response. Default = 100 "),
            @Parameter(name = "sortOrder", description = "ASC or DESC"),
            @Parameter(name = "sortBy", description = "condition to sort"),
    })
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

    @Operation(summary = "Range query by minutes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success. "),
            @ApiResponse(responseCode = "400", description = "Bad request. ")
    })
    @Parameters(value = {
            @Parameter(name = "min", required = true, description = "Min value of the range. "),
            @Parameter(name = "max", required = true, description = "Max value of the range. "),
            @Parameter(name = "size", description = "Number of documents in the response. Default = 100 "),
            @Parameter(name = "sortOrder", description = "ASC or DESC"),
            @Parameter(name = "sortBy", description = "condition to sort"),
    })
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
