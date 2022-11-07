package co.empathy.academy.search.services;

import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.repositories.deleting.DeleteRepository;
import co.empathy.academy.search.repositories.indexing.IndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieService {

    private Map<UUID, Movie> movies;

    @Autowired
    private IndexRepository<Movie> indexingRepository;

    @Autowired
    private DeleteRepository<Movie> deleteRepository;

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

    public String deleteMovie(UUID id) {
        return deleteRepository.deleteDocument(id.toString());
    }

    public String indexDocument(Movie movie) {
        return indexingRepository.indexDocument(movie);
    }
    public boolean createIndex() {
        return indexingRepository.createIndex();
    }

    public String indexMovie(Movie movie) {
        return indexingRepository.indexDocument(movie);
    }

    public List<Movie> synchronousBulkIndexing(List<Movie> movieList) {
        return indexingRepository.synchronousBulkIndexing(movieList);
    }

}
