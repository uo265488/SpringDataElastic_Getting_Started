package co.empathy.academy.search.services;

import co.empathy.academy.search.config.LowRestClientConfig;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElasticService {

    @Autowired
    private LowRestClientConfig lowClientConfig;

    public String getElasticEndpointReport(String endpoint)  {
        String report;
        try {
            Response response=
                    lowClientConfig.lowRestClient().performRequest(new Request("GET", endpoint));
            report = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("IOException from the Low Rest Client. ");
        }
        return report;
    }
}
