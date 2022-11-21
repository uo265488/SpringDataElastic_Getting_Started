package co.empathy.academy.search.repositories.searching;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationRange;
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
import java.util.ArrayList;
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
        return response;
    }

    private SearchRequest getSearchRequest(Query query, int size) {
        return SearchRequest.of(s -> s
                .index(Indices.MOVIE_INDEX)
                .query(query)
                .size(size)
                .aggregations("facetMinutes", a -> a
                                .range(h -> h
                                        .field(FieldAttr.Movie.MINUTES_FIELD)
                                        .ranges(getAggregationRange(100, 200, 100))
                                )
                        )
                .aggregations("facetGenre", a -> a
                        .terms(h -> h
                                .field(FieldAttr.Movie.GENRES_FIELD)
                        )
                )
                .aggregations("facetTitleType", a -> a
                        .terms(h -> h
                                .field(FieldAttr.Movie.TITLE_TYPE_FIELD)
                        )
                ).aggregations("facetStartYear", a -> a
                        .range(h -> h
                                .field(FieldAttr.Movie.MINUTES_FIELD)
                                .ranges(getAggregationRange(1000, 2000, 200))
                        )
                ).aggregations("facetAverageRating", a -> a
                        .range(h -> h
                                .field(FieldAttr.Movie.RATING_FIELD)
                                .ranges(getAggregationRange(0, 10, 1))
                        )
                )
        );
    }

    private List<AggregationRange> getAggregationRange(int min, int max, int interval) {
        List<AggregationRange> list = new ArrayList<>();
        list.add(AggregationRange.of(a ->
                a.to(String.valueOf(min))
                ));
        for(int i = min; i < max; i = i + interval) {
            int finalI = i;
            list.add(AggregationRange.of(a ->
                    a.from(String.valueOf(finalI)).to(String.valueOf(finalI + interval))
            ));
        }
        list.add(AggregationRange.of(a ->
                a.from(String.valueOf(max))
        ));

        return list;
    }
}
