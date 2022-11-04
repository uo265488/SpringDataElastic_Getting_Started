package co.empathy.academy.search.services;

import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.empathy.academy.search.config.ElasticsearchClientConfig;
import co.empathy.academy.search.documents.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class MovieService {


    private Map<UUID, Movie> movies;

    @Autowired
    private ElasticsearchClientConfig esConfig;

    public MovieService() {
        movies = new HashMap<>();
    }

    public Movie findMovieById(String id) {
        return movies.get(id);
    }

    public Movie saveMovie(Movie movie) {
        UUID id = UUID.randomUUID();
        //To not use setter, avoiding altering state of movie after construction
        Movie newMovie = movie.withId(id.toString());
        movies.put(id, newMovie);
        return newMovie;
    }

    public Movie deleteMovie(UUID id) {
        return movies.remove(id);
    }

    public String indexMovie(Movie movie) {
        IndexResponse response;
        try {
            response = esConfig.getEsClient()
                    .index(i -> i.index("movie")
                            .id("" + movie.getId())
                            .document(movie)
                    );
        } catch (Exception e) {
            return "The indexing of the movie could not be performed.";
        }
        return "" + response.version();
    }

    public List<Movie> bulkIndexing(List<Movie> movieList) {

        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Movie movie : movieList) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index("movies")
                            .id(movie.getId())
                            .document(movie)
                    )
            );
        }
        BulkResponse result;
        try {
            result = esConfig.getEsClient().bulk(br.build());
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }

        if (result.errors()) {
            return new ArrayList<>();
        }
        return movieList;
    }
}
