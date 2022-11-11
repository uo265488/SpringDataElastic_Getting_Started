package co.empathy.academy.search.repositories.searching;

import co.elastic.clients.elasticsearch.core.search.Hit;

import java.util.List;

public interface SearchRepository<Document> {

    /**
     * Performs a filterQuery for the field "fieldName" and the value "value"
     * @param fieldName
     * @return list of hits
     */
    List<Hit<Document>> filterQuery(String fieldName, String value);


}
