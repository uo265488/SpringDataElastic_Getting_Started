package co.empathy.academy.search.repositories.searching;


import co.elastic.clients.elasticsearch._types.query_dsl.Query;

public interface QueryFactory {

    /**
     * Performs a filterQuery for the field "fieldName" and the value "value"
     * @param fieldName
     * @return list of hits
     */
    Query getFilterQuery(String fieldName, String value);

    /**
     * Performs a rangeQuery for the field "fieldName" in the range
     * @param fieldName
     * @return list of hits
     */
    Query getRangeQuery(String fieldName, int min, int max);


}
