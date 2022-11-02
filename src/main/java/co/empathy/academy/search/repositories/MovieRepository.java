package co.empathy.academy.search.repositories;

import co.empathy.academy.search.documents.Movie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends ElasticsearchRepository<Movie, String> {
}
