package co.empathy.academy.search.services;

import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.helpers.parser.BaseParser;
import co.empathy.academy.search.helpers.parser.MovieParser;
import co.empathy.academy.search.repositories.deleting.DeleteRepository;
import co.empathy.academy.search.repositories.indexing.IndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

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
        Movie newMovie = new Movie();
        movies.put(id, newMovie);
        return newMovie;
    }

    public String deleteMovie(UUID id) {
        return deleteRepository.deleteDocument(id.toString());
    }

    public String indexDocument(Movie movie) {
        return indexingRepository.indexDocument(movie);
    }
    public String createIndex() {
        return indexingRepository.createIndex();
    }

    public String indexMovie(Movie movie) {
        return indexingRepository.indexDocument(movie);
    }

    /**
     * Performs synchronous bulk indexing of 'title.basics.tsv' file
     * @param
     * @return
     */
    public boolean synchronousBulkIndexingMovies(
            MultipartFile titleBasics, MultipartFile ratings, MultipartFile akas, MultipartFile principals) {

        //indexingRepository.createIndex();

        int numMoviesPerExecution = 50000;
        int i = 0;
        BaseParser movieParser = new MovieParser(titleBasics, ratings, akas, principals);
        List<Movie> movies = movieParser.parseMovies(numMoviesPerExecution);

        while(!movies.isEmpty()) {
            indexingRepository.synchronousBulkIndexing(
                    movies.stream().filter(m -> m != null).collect(Collectors.toList()));

            movies = movieParser.parseMovies(numMoviesPerExecution);
            System.out.println(i++);
        }
        return true;
    }

    public boolean synchronousBulkIndexingRatings(MultipartFile multipartFile) {
        return true;
    }
}
