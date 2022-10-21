package co.empathy.academy.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LowRestClientConfig {

    @Bean
    public RestClient lowRestClient() {

        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")).build();

        return restClient;
    }

}
