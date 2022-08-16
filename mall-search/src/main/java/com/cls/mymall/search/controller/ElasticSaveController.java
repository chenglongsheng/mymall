package com.cls.mymall.search.controller;

import com.cls.mymall.common.exception.BizCode;
import com.cls.mymall.common.to.es.SkuEsModel;
import com.cls.mymall.common.utils.R;
import com.cls.mymall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/search/save")
public class ElasticSaveController {

    @Autowired
    private ProductSaveService productSaveService;

    @PostMapping("/productStatusUp")
    public R productStatusUp(@RequestBody List<SkuEsModel> modelList) {
        Boolean status = false;
        try {
            status = productSaveService.productStatusUp(modelList);
        } catch (Exception e) {
            log.error("ElasticSaveController商品上架错误{}", e);
            return R.error(BizCode.PRODUCT_UP_EXCEPTION.getCode(), BizCode.PRODUCT_UP_EXCEPTION.getMsg());
        }
        if (!status) {
            return R.ok();
        } else {
            return R.error(BizCode.PRODUCT_UP_EXCEPTION.getCode(), BizCode.PRODUCT_UP_EXCEPTION.getMsg());
        }

    }

}
