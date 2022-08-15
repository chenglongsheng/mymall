package com.cls.mymall.search;

import com.alibaba.fastjson.JSON;
import com.cls.mymall.search.config.MallElasticSearchConfig;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class MallSearchApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    void contextLoads() {
    }

    @Test
    void indexData() throws IOException {
        IndexRequest indexRequest = new IndexRequest("user");
        indexRequest.id("1");
        User user = new User();
        user.setUserName("张三");
        user.setAge(18);
        user.setGender("男");
        String jsonString = JSON.toJSONString(user);
        IndexRequest request = indexRequest.source(jsonString, XContentType.JSON);
        IndexResponse index = client.index(request, MallElasticSearchConfig.COMMON_OPTIONS);

        System.out.println(index);
    }

    @Data
    static
    class User {
        private String userName;
        private String gender;
        private Integer age;
    }

}
