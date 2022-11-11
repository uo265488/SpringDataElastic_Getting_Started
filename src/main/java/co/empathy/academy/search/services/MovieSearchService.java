package co.empathy.academy.search.services;

import co.elastic.clients.elasticsearch.core.search.Hit;
import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.repositories.searching.MovieSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class MovieSearchService {

    @Autowired
    private MovieSearchRepository repository;

    /**
     * Calls the searchRepository to obtain the hits of the filterQuery
     * @param fieldName name of the field
     * @param value value of the field
     * @return
     */
    public List<Hit<Movie>> filterQuery(String fieldName, String value) {
        return repository.filterQuery(fieldName, value);
    }

}
