package co.empathy.academy.search.repositories.deleting;

import co.empathy.academy.search.config.ElasticsearchClientConfig;
import co.empathy.academy.search.documents.Movie;
import co.empathy.academy.search.helpers.Indices;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class MovieDeleteRepository implements DeleteRepository<Movie> {

    @Autowired
    private ElasticsearchClientConfig esClientConfig;

    @Override
    public String deleteDocument(String _id) {
        try {
            return esClientConfig.getEsClient()
                    .delete(d -> d.id(_id)).result().toString();

        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    @Override
    public boolean deleteIndex(String indexName) {
        try {
            DeleteIndexRequest request = new DeleteIndexRequest(Indices.MOVIE_INDEX);

            return esClientConfig.elasticsearchClient()
                    .indices().delete(request, RequestOptions.DEFAULT)
                    .isAcknowledged();

        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
