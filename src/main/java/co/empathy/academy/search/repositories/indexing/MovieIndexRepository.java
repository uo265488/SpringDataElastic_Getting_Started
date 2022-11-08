package co.empathy.academy.search.repositories.indexing;

import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.empathy.academy.search.config.ElasticsearchClientConfig;
import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.helpers.Indices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieIndexRepository implements IndexRepository<Movie> {

    @Autowired
    private ElasticsearchClientConfig elasticsearchClientConfig;

    @Override
    public boolean createIndex() {
        CreateIndexResponse response;
        try {
            response = elasticsearchClientConfig.getEsClient()
                    .indices()
                    .create(c -> c.index(Indices.MOVIE_INDEX));
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
        return response.acknowledged();
    }

    @Override
    public String indexDocument(Movie document) {
        IndexResponse response;
        try {
            response = elasticsearchClientConfig.getEsClient()
                    .index(i -> i.index(Indices.MOVIE_INDEX)
                            .id(document.getId())
                            .document(document)
                    );
        } catch (Exception e) {
            return "The indexing of the movie could not be performed.";
        }
        return "" + response.version();
    }

    @Override
    public List<Movie> synchronousBulkIndexing(List<Movie> movieList) {
        BulkRequest.Builder br = new BulkRequest.Builder();

        for (Movie movie : movieList) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(Indices.MOVIE_INDEX)
                            .id(movie.getId())
                            .document(movie)
                    )
            );
            //Logger.getLogger(this.getClass().getName()).log(Level.INFO, movie.toString());
        }
        BulkResponse result;
        try {
            result = elasticsearchClientConfig.getEsClient().bulk(br.build());
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
        if (result.errors()) {
            return new ArrayList<>();
        }

        return movieList;
    }

    @Override
    public List<Movie> asynchronousBulkIndexing(List<Movie> documentsList) {
        throw new RuntimeException("Asynchronous Bulk Indexing Not yet implemented.");
    }
}
