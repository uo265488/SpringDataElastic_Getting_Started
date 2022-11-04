package co.empathy.academy.search.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.empathy.academy.search.config.ElasticsearchClientConfig;
import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.helper.Indices;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public List<Movie> synchronousBulkIndexing(List<Movie> movieList) {

        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Movie movie : movieList) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(Indices.MOVIE_INDEX)
                            .id(movie.getId())
                            .document(movie)
                    )
            );
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, movie.toString());
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
