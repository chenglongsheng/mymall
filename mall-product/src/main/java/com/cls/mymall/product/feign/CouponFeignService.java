package com.cls.mymall.product.feign;

import com.cls.mymall.common.utils.R;
import com.cls.mymall.coupon.entity.SpuBoundsEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mymall-coupon")
public interface CouponFeignService {

    @RequestMapping("coupon/spubounds/save")
    R save(@RequestBody SpuBoundsEntity spuBounds);

}
