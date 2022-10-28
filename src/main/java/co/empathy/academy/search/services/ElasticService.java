package co.empathy.academy.search.services;

import co.empathy.academy.search.config.LowRestClientConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

@Service
public class ElasticService {

    @Autowired
    private LowRestClientConfig lowClientConfig;

    public String performGETRequest(String endpoint)  {
        String report;
        try {
            Response response=
                    lowClientConfig.lowRestClient().performRequest(new Request("GET", endpoint));
            report = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            //BETTER APPROACH???? THROW EXCEPTION OR INFORM OF BAD USE
            //throw new RuntimeException("IOException from the Low Rest Client. ");
            report = "There is something wrong with your petition. ";
        }
        return report;
    }

    public String performPUTRequest(String index)  {
        String report;
        try {
            Response response=
                    lowClientConfig.lowRestClient().performRequest(new Request("PUT", index));
            report = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            //BETTER APPROACH???? THROW EXCEPTION OR INFORM OF BAD USE
            //throw new RuntimeException("IOException from the Low Rest Client. ");
            report = "There is something wrong with your petition. ";
        }
        return report;
    }

    /**
     * Performs POST request of a document on the index given
     * @param indexName
     * @param body
     * @return
     */
    public String indexDocument(String indexName, Map<String, String> body) {
        String report;
        Request request;
        try {
            request = new Request("POST", "/" + indexName + "/_doc");
            request.setEntity(new NStringEntity(
                            new ObjectMapper().writeValueAsString(body),
                            ContentType.APPLICATION_JSON));

            Response response=
                    lowClientConfig.lowRestClient().performRequest(request);
            report = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            //BETTER APPROACH???? THROW EXCEPTION OR INFORM OF BAD USE
            //throw new RuntimeException("IOException from the Low Rest Client. ");
            report = "There is something wrong with your petition. ";
        }
        return report;
    }
}
