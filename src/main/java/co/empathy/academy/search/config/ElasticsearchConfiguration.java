package co.empathy.academy.search.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "co.empathy.academy.search.repositories")
@ComponentScan(basePackages = {"co.empathy.academy.search"})
public class ElasticsearchConfiguration /*extends AbstractElasticsearchConfiguration */{

    //DID NOT WORK
    /*@Value("${elasticsearch.url")
    public String elasticsearchurl;*/

    /*
    @Override

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration configuration =
                ClientConfiguration
                        .builder()
                .connectedTo("localhost:9200")
                        .build();

        return RestClients.create(configuration).rest();
    }
*/

}