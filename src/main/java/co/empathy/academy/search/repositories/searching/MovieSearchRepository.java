package co.empathy.academy.search.repositories.searching;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import co.empathy.academy.search.config.ElasticsearchClientConfig;
import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.helpers.Indices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class MovieSearchRepository implements SearchRepository<Movie> {

    @Autowired
    private ElasticsearchClientConfig elasticsearchClientConfig;

    @Override
    public List<Hit<Movie>> filterQuery(String fieldName, String value) {
        return executeQuery(MatchQuery.of(m -> m
                .field(fieldName)
                .query(value)
        )._toQuery());
    }

    @Override
    public List<Hit<Movie>> rangeQuery(String fieldName, int min, int max) {
        return executeQuery( RangeQuery.of(r -> r
                .field(fieldName)
                .lte(JsonData.of(max))
                .gte(JsonData.of(min))
        )._toQuery());
    }

    /**
     * Executes query given by parameter
     * @param query
     * @return
     */
    public List<Hit<Movie>> executeQuery(Query query) {
        SearchResponse<Movie> response;
        try {
            response = elasticsearchClientConfig.getEsClient()
                    .search(s -> s
                                    .index(Indices.MOVIE_INDEX)
                                    .query(query)
                                    .size(10000),
                            Movie.class
                    );
        } catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return response.hits().hits();
    }
}
