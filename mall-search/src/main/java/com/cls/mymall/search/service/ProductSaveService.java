package com.cls.mymall.search.service;

import com.cls.mymall.common.to.es.SkuEsModel;

import java.util.List;

public interface ProductSaveService {
    Boolean productStatusUp(List<SkuEsModel> modelList);
}
