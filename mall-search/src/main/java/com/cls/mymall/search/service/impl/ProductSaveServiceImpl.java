package com.cls.mymall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.cls.mymall.common.to.es.SkuEsModel;
import com.cls.mymall.search.config.MallElasticSearchConfig;
import com.cls.mymall.search.constant.EsConstant;
import com.cls.mymall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public Boolean productStatusUp(List<SkuEsModel> modelList) {

        //1、给es建立索引。建好映射关系
        //2、保存数据

        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel model : modelList) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(model.getSkuId().toString());
            String s = JSON.toJSONString(model);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }

        try {
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, MallElasticSearchConfig.COMMON_OPTIONS);
            boolean b = bulk.hasFailures();
            List<String> collect = Arrays.stream(bulk.getItems()).map(BulkItemResponse::getId).collect(Collectors.toList());
            log.info("商品上架成功：{},{}", collect, bulk);
            return b;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
