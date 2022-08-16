package com.cls.mymall.product.feign;

import com.cls.mymall.common.to.es.SkuEsModel;
import com.cls.mymall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("mall-search")
public interface SearchFeignService {

    @PostMapping("/search/save/productStatusUp")
    R productStatusUp(@RequestBody List<SkuEsModel> modelList);

}
