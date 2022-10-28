package co.empathy.academy.search.services;

import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.empathy.academy.search.config.ElasticsearchClientConfig;
import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository repository;

    @Autowired
    private ElasticsearchClientConfig esConfig;

    @Autowired
    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public void save(final Movie movie) {
        repository.save(movie);
    }

    public Movie findById(final String id) {
        return repository.findById(id).orElse(null);
    }

    public String indexMovie(Movie movie) {
        IndexResponse response;
        try {
            response = esConfig.getEsClient()
                    .index(i -> i.index("movie")
                            .id(movie.getId())
                            .document(movie)
                    );
        } catch (Exception e) {
            return "The indexing of the movie could not be performed.";
        }
        return "" + response.version();
    }

}
