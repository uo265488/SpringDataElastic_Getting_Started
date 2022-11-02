package co.empathy.academy.search.repositories;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import co.empathy.academy.search.config.ElasticsearchClientConfig;
import co.empathy.academy.search.config.ElasticsearchConfiguration;
import co.empathy.academy.search.documents.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieQueryRepository {
    @Autowired
    private ElasticsearchClientConfig esConfig;

    //MAKE QUERY EXECUTOR (COMMAND)
    public List<Hit<Movie>> findByTitle(String title) {
        SearchResponse<Movie> response;
        try {
            response = esConfig.getEsClient().search(s -> s
                            .index("movie")
                            .query(q -> q
                                    .match(t -> t
                                            .field("title")
                                            .query(title)
                                    )
                            ),
                    Movie.class

            );
            TotalHits total = response.hits().total();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return response.hits().hits();
    }

    /**
     * GET /<index>/_search
     * {
     *   "query": {
     *     "multi_match" : {
     *       "query":    "batman",
     *       "fields": [ "title", "synopsis" ]
     *     }
     *   }
     * }
     * @param fields
     * @param searchtext
     * @return
     */
    public List<Hit<Movie>> query1(ArrayList<String> fields, String searchtext) {
        SearchResponse<Movie> response;
        try {
            response = esConfig.getEsClient().search(s -> s
                            .index("movie")
                            .query(q -> q
                                    .multiMatch(
                                            mm ->
                                                    mm.fields(fields)
                                                            .query(searchtext)
                                    )
                            ),
                    Movie.class

            );
            TotalHits total = response.hits().total();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return response.hits().hits();
    }

    /**
     * GET /<index>/_search
     * {
     *   "query": {
     *     "term": {
     *       "movie.id": {
     *         "value": "0267544",
     *         "boost": 1.0
     *       }
     *     }
     *   }
     * }
     */
    public List<Hit<Movie>> query2(String term, Long value, float boost) {
        SearchResponse<Movie> response;
        try {
            response = esConfig.getEsClient().search(s -> s
                            .index("movie")
                            .query(q -> q
                                    .term(
                                            mm ->
                                                    mm.field("movie.id")
                                                            .value(value)
                                                            .boost(boost)
                                    )
                            ),
                    Movie.class
            );
            TotalHits total = response.hits().total();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return response.hits().hits();
    }

    /**
     * GET /<index>/_search
     * {
     *   "query": {
     *     "term": {
     *       "movie.id": {
     *         "value": "0267544",
     *         "boost": 1.0
     *       }
     *     }
     *   }
     * }
     */
    public List<Hit<Movie>> query3(List<String> terms, Long value, float boost) {
        SearchResponse<Movie> response;
        try {
            response = esConfig.getEsClient().search(s -> s
                            .index("movie")
                            .query(q -> q
                                    .terms(
                                            t -> t.boost(boost)
                                                    .field("movie.id")
                                                    //values???
                                    )
                            ),
                    Movie.class
            );
            TotalHits total = response.hits().total();
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return response.hits().hits();
    }


}
