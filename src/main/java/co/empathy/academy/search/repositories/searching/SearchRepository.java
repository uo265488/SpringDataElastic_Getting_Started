package co.empathy.academy.search.repositories.searching;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;

public interface SearchRepository {

    /**
     * Query executor
     * @param query
     * @return List<Hit<Document>>
     */
    SearchResponse executeQuery(Query query, int size);
}
