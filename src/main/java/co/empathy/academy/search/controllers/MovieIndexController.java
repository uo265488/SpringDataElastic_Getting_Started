package co.empathy.academy.search.controllers;

import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.services.indexing.MovieIndexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/")
public class MovieIndexController {
    @Autowired
    private MovieIndexService service;

    @Operation(summary = "Movie's bulk indexing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success. "),
            @ApiResponse(responseCode = "500", description = "Error indexing movies. ")
    })
    @Parameters(value = {
            @Parameter(name = "basics", required = true, description = "title.basics.tsv"),
            @Parameter(name = "ratings", required = true, description = "title.ratings.tsv"),
            @Parameter(name = "akas", required = true, description = "title.akas.tsv"),
            @Parameter(name = "principals", required = true, description = "title.principals.tsv"),
    })
    @PostMapping("index")
    public ResponseEntity bulkIndexing(
            @RequestParam("file") MultipartFile basics,
            @RequestParam("file2") MultipartFile ratings,
            @RequestParam("file3") MultipartFile akas,
            @RequestParam("file4") MultipartFile principals) {

        boolean success = service.synchronousBulkIndexingMovies(basics, ratings, akas, principals);

        return success
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * Indexing a movie document in the movie index
     */
    @PostMapping("/indexMovie")
    public ResponseEntity<String> indexMovie(@RequestBody Movie movie) {

        return ResponseEntity.ok(service.indexDocument(movie));
    }

    @Operation(summary = "Create the Movie index")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success. "),
            @ApiResponse(responseCode = "500", description = "Not created. ")
    })
    @PostMapping("create")
    public ResponseEntity createIndex() {
        boolean success = Boolean.valueOf(service.createIndex());

        return success
                ? ResponseEntity.created(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/movies")
                    .buildAndExpand().toUri()).build()
                : ResponseEntity.internalServerError().build();
    }



    @PostMapping
    public ResponseEntity<Movie> save(@RequestBody final Movie movie) {
        Movie newMovie = null;//service.saveMovie(movie);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newMovie.getId()).toUri();

        return ResponseEntity.created(uri).body(newMovie);
    }

}
