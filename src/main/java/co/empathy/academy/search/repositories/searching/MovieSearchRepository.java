package co.empathy.academy.search.repositories.searching;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.empathy.academy.search.config.ElasticsearchClientConfig;
import co.empathy.academy.search.documents.FieldAttr;
import co.empathy.academy.search.helpers.Indices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MovieSearchRepository implements SearchRepository {

    @Autowired
    private ElasticsearchClientConfig elasticsearchClientConfig;

    /**
     * Executes query given by parameter
     * @param query
     * @return
     */
    @Override
    public SearchResponse executeQuery(Query query, int size, String sortOrder, String sortBy) {
        SearchResponse response;
        try {
            response = elasticsearchClientConfig.getEsClient()
                    .search(getSearchRequest(query, size, sortOrder, sortBy), Object.class);
        } catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return response;
    }

    /**
     * Creates a sorted Search Request
     * @param query
     * @param size
     * @param sortOrder
     * @param sortBy
     * @return
     */
    private SearchRequest getSearchRequest(Query query, int size, String sortOrder, String sortBy) {
        return SearchRequest.of(s -> s
                .index(Indices.MOVIE_INDEX)
                .query(query)
                .size(size)
                .aggregations(getAggregations())
                .sort(getSortOptions(sortOrder, sortBy))
        );
    }

    /**
     * Returns the map with aggregations
     * @return
     */
    private Map<String, Aggregation> getAggregations() {
        Map<String, Aggregation> map = new HashMap<>();
        map.put("facetMinutes", new RangeAggregation.Builder()
                .field(FieldAttr.Movie.MINUTES_FIELD)
                .ranges(getAggregationRange(100,200,100)).build()._toAggregation());
        map.put("facetStartYear", new RangeAggregation.Builder()
                .field(FieldAttr.Movie.START_YEAR_FIELD)
                .ranges(getAggregationRange(1400,2000,200)).build()._toAggregation());
        map.put("facetAverageRating", new RangeAggregation.Builder()
                .field(FieldAttr.Movie.RATING_FIELD)
                .ranges(getAggregationRange(0,10,1)).build()._toAggregation());
        map.put("facetGenre", new TermsAggregation.Builder()
                        .field(FieldAttr.Movie.GENRES_FIELD).build()._toAggregation());
        map.put("facetTitleType", new TermsAggregation.Builder()
                .field(FieldAttr.Movie.TITLE_TYPE_FIELD).build()._toAggregation());

        return map;
    }

    /**
     * Returns the list of aggregation ranges
     * @param min
     * @param max
     * @param interval
     * @return
     */
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

    /**
     * Parses the ordering and orderBy to Sort Options
     * @param ordering
     * @param orderBy
     * @return
     */
    private List<SortOptions> getSortOptions(String ordering, String orderBy) {
        SortOptions sort;
        List<SortOptions> list = new ArrayList<SortOptions>();
        if(ordering.equals("asc")) {
            sort = new SortOptions.Builder()
                    .field(f -> f.field(orderBy).order(SortOrder.Asc)).build();
            list.add(sort);
        } else if(ordering.equals("desc")) {
            sort = new SortOptions.Builder()
                    .field(f -> f.field(orderBy).order(SortOrder.Desc)).build();
            list.add(sort);
        }
        return list;
    }

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
}
