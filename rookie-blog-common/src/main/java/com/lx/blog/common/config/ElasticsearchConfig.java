package com.lx.blog.common.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李旭
 * @date 2025/12/03
 * @description Elasticsearch客户端配置
 */
@Configuration
@RequiredArgsConstructor
public class ElasticsearchConfig {

    @Value("${es.host:http://localhost:9200}")
    private String esHost;

    @Bean
    public RestClient restClient() {
        HttpHost host = HttpHost.create(esHost);
        return RestClient.builder(host).build();
    }

    @Bean
    public ElasticsearchTransport transport(@NotNull RestClient restClient) {
        return new RestClientTransport(restClient, new JacksonJsonpMapper());
    }

    @Bean
    public ElasticsearchClient elasticsearchClient(@NotNull ElasticsearchTransport transport) {
        return new ElasticsearchClient(transport);
    }
}

