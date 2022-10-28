package co.empathy.academy.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchClientConfig {

    @Bean
    public RestClient lowRestClient() {

        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")).build();

        return restClient;
    }


    @Bean
    public ElasticsearchClient getEsClient() {

        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")).build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // And create the API client
        ElasticsearchClient client = new ElasticsearchClient(transport);

        return client;
    }

}
