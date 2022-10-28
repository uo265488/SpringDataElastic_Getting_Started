package co.empathy.academy.search.services;

import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository repository;

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
}
