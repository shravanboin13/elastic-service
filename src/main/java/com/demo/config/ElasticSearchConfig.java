package com.demo.config;

import com.demo.model.Product;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.data.elasticsearch.core.DefaultEntityMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.demo.dao")
public class ElasticSearchConfig {
    @Value("${elasticsearch.host}")
    private String esHost;
    @Value("${elasticsearch.port}")
    private int esPort;
    @Value("${elasticsearch.clustername}")
    private String esClusterName;
    @Bean
    public Client client() throws Exception {

        Settings esSettings = Settings.settingsBuilder().put("client.transport.sniff", true).put("name","Martha Johansson")
                .put("cluster.name", esClusterName)
                .build();

        Client client =TransportClient.builder()
                .settings(esSettings)
                .build()
                .addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName(esHost), esPort));

    return client;}

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
      ElasticsearchTemplate elastic =  new  ElasticsearchTemplate(client(),new DefaultEntityMapper());
elastic.putMapping(Product.class);
        return new ElasticsearchTemplate(client(),new DefaultEntityMapper());
    }



}
