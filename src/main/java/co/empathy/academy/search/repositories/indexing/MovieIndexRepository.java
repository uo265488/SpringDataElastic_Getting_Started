package co.empathy.academy.search.repositories.indexing;

import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.empathy.academy.search.config.ElasticsearchClientConfig;
import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.helpers.Indices;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.List;

@Repository
public class MovieIndexRepository implements IndexRepository<Movie> {

    @Autowired
    private ElasticsearchClientConfig elasticsearchClientConfig;

    private static final String configFilePath =
            "/Users/oscarperez/Desktop/SpringDataElastic_Getting_Started/src/main/resources";
    private static final String fileName = Indices.MOVIE_INDEX + ".json";

    @Override
    public String createIndex() {
        try {
            elasticsearchClientConfig.getEsClient().indices().create(i -> i.index(Indices.MOVIE_INDEX));
            return updateMapping();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the mapping of the MOVIE index
     * @return
     * @throws IOException
     */
    private String updateMapping() throws IOException {
        return elasticsearchClientConfig.getEsClient()
                .indices()
                .putMapping(
                        m -> m.index(Indices.MOVIE_INDEX)
                                .withJson(this.getClass().getClassLoader().getResourceAsStream(fileName)))
                .toString();
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
        }
        BulkResponse result;
        try {
            result = elasticsearchClientConfig.getEsClient().bulk(br.build());
        } catch (IOException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return movieList;
    }

    @Override
    public List<Movie> asynchronousBulkIndexing(List<Movie> documentsList) {
        throw new RuntimeException("Asynchronous Bulk Indexing Not yet implemented.");
    }
}
