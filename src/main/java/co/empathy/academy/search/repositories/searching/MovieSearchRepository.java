package co.empathy.academy.search.repositories.searching;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
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
        System.out.println(fieldName + value );
        try {
            response = elasticsearchClientConfig.getEsClient()
                    .search(s -> s
                                    .index(Indices.MOVIE_INDEX)
                                    .query(q -> q
                                            .match(t -> t
                                                    .field(fieldName)
                                                    .query(value)
                                            )
                                    ),
                            Movie.class
                    );
        } catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        System.out.print(response.hits());
        return response.hits().hits();
    }
}
