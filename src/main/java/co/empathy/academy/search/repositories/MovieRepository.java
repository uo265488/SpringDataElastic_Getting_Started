package co.empathy.academy.search.repositories;

import co.empathy.academy.search.documents.Movie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MovieRepository extends ElasticsearchRepository<Movie, String> {

}
