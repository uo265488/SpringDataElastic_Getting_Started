package co.empathy.academy.search.config;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ElasticsearchConfiguration /* extends AbstractElasticsearchConfiguration*/ {

    private final ElasticsearchProperties elasticsearchProperties;
/*
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticsearchProperties.getHostAndPort())
                .withSocketTimeout(elasticsearchProperties.getSocketTimeout())
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
    */

}