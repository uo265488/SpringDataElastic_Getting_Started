package co.empathy.academy.search.services;

import co.empathy.academy.search.config.LowClientConfig;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElasticService {

    @Autowired
    private LowClientConfig lowClientConfig;

    public String performRequestToLowLevelClient(String endpoint) {
        String report = lowClientConfig.performRequest(endpoint);

        return report;
    }
}
