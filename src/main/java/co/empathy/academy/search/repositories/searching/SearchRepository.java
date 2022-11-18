package co.empathy.academy.search.repositories.searching;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.search.Hit;

import java.util.List;

public interface SearchRepository<Document> {

    /**
     * Query executor
     * @param query
     * @return List<Hit<Document>>
     */
    List<Hit<Document>> executeQuery(Query query, int size);
}
