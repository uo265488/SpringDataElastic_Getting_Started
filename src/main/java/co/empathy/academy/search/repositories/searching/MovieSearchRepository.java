package co.empathy.academy.search.repositories.searching;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.empathy.academy.search.config.ElasticsearchClientConfig;
import co.empathy.academy.search.documents.FieldAttr;
import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.helpers.Indices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class MovieSearchRepository implements SearchRepository {

    @Autowired
    private ElasticsearchClientConfig elasticsearchClientConfig;

    /**
     * Adds a histogram aggregation for runtimeMinutes
     * @param builder
     */
    private void addHistogramAggregation(SearchRequest.Builder builder) {
        builder.aggregations("runtimeMinutes-histogram", a -> a
                .histogram(h -> h
                        .field("runtimeMinutes")
                        .interval(20.0)
                )
        );
    }

    private Query getTermsQuery(List<FieldValue> filters, String field) {
        return Query.of(q ->
                q.terms(TermsQuery.of(tsq ->
                        tsq.field(field)
                                .terms(TermsQueryField.of(tf -> tf.value(
                                        filters
                                ))))));
    }

    /**
     * Executes query given by parameter
     * @param query
     * @return
     */
    @Override
    public SearchResponse executeQuery(Query query, int size) {
        SearchResponse response;
        try {
            response = elasticsearchClientConfig.getEsClient()
                    .search(getSearchRequest(query, size), Object.class);
        } catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        //System.out.println(response.aggregations());
        return response;
    }

    private SearchRequest getSearchRequest(Query query, int size) {
        return SearchRequest.of(s -> s
                .index(Indices.MOVIE_INDEX)
                .query(query)
                .size(size)
                        .aggregations("facetMinutes", a -> a
                                .histogram(h -> h
                                        .field(FieldAttr.Movie.MINUTES_FIELD)
                                        .interval(50.0)
                                )
                        )
                .aggregations("facetGenre", a -> a
                        .terms(h -> h
                                .field(FieldAttr.Movie.GENRES_FIELD)
                        )
                )
        );
    }
}
