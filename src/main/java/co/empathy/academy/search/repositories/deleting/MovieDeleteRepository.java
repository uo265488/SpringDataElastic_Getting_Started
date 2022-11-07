package co.empathy.academy.search.repositories.deleting;

import co.empathy.academy.search.config.ElasticsearchClientConfig;
import co.empathy.academy.search.documents.Movie;
import org.elasticsearch.index.mapper.DocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

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
        return false;
    }
}
