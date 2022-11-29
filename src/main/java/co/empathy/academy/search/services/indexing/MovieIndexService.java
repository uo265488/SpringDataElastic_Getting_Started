package co.empathy.academy.search.services.indexing;

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
public class MovieIndexService {

    private Map<UUID, Movie> movies;

    @Autowired
    private IndexRepository<Movie> indexingRepository;

    @Autowired
    private DeleteRepository<Movie> deleteRepository;

    public MovieIndexService() {
        movies = new HashMap<>();
    }

    public String indexDocument(Movie movie) {
        return indexingRepository.indexDocument(movie);
    }
    public String createIndex() {
        return indexingRepository.createIndex();
    }

    /**
     * Performs synchronous bulk indexing of 'title.basics.tsv' file
     * @param ratings, akas, titleBasics and principals files for indexing
     * @return
     */
    public boolean synchronousBulkIndexingMovies(
            MultipartFile titleBasics, MultipartFile ratings, MultipartFile akas, MultipartFile principals) {

        deleteRepository.deleteIndex();
        indexingRepository.createIndex();

        int numMoviesPerExecution = 50000;
        BaseParser movieParser = new MovieParser(titleBasics, ratings, akas, principals);
        List<Movie> movies = movieParser.parseMovies(numMoviesPerExecution);
        while(!movies.isEmpty()) {
            indexingRepository.synchronousBulkIndexing(
                    movies.stream().filter(m -> m != null).collect(Collectors.toList()));
            movies = movieParser.parseMovies(numMoviesPerExecution);
        }
        return true;
    }

    /**
     * Performs asynchronous bulk indexing of 'title.basics.tsv' file
     * @param ratings, akas, titleBasics and principals files for indexing
     * @return
     */
    public boolean asynchronousBulkIndexingRatings(
            MultipartFile titleBasics, MultipartFile ratings, MultipartFile akas, MultipartFile principals) {
        throw new RuntimeException("Asynchoronous bulk indexing not yet implemented.");
    }
}
