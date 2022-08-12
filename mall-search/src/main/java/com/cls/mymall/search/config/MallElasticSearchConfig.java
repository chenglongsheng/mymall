package com.cls.mymall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MallElasticSearchConfig {

    @Bean
    public RestHighLevelClient esRestClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost("47.106.196.152", 9200, "http")));
    }

}
