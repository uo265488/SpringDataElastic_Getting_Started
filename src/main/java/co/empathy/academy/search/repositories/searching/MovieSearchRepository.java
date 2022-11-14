package co.empathy.academy.search.repositories.searching;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
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
        SearchResponse<Movie> response;
        try {
            response = elasticsearchClientConfig.getEsClient()
                    .search(s -> s
                                    .index(Indices.MOVIE_INDEX)
                                    .query(q -> q
                                            .match(t -> t
                                                    .field(fieldName)
                                                    .query(value)
                                            )
                                )
                                    .size(10000),
                            Movie.class
                    );
        } catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return response.hits().hits();
    }

    public List<Hit<Movie>> rangeQuery(String fieldName, int min, int max) {
        SearchResponse<Movie> response;
        try {
            response = elasticsearchClientConfig.getEsClient()
                    .search(s -> s
                                    .index(Indices.MOVIE_INDEX)
                                    .query(q ->
                                            q.range(m ->
                                                    m.field(fieldName)
                                                            .gt(JsonData.of(min))
                                                            .lt(JsonData.of(max)
                                                            )
                                            )

                                    )
                                    .size(10000),
                            Movie.class
                    );
        } catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return response.hits().hits();
    }
}
