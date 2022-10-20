package co.empathy.academy.search.config;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class LowClientConfig {

    public String performRequest(String endpoint) {

        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")).build();

        Request request = new Request(
                "GET",
                endpoint);

        String report = "";

        try {
            Response response = restClient.performRequest(request);
            report = EntityUtils.toString(response.getEntity());
            restClient.close();

        } catch (IOException e){
            //TO Improve exception handling
            report = "It was not possible to obtain the endpoint report. ";
        }
        return report;
    }

}
