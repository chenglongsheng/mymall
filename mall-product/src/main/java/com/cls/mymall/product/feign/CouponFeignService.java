package com.cls.mymall.product.feign;

import com.cls.mymall.common.utils.R;
import com.cls.mymall.coupon.entity.MemberPriceEntity;
import com.cls.mymall.coupon.entity.SkuFullReductionEntity;
import com.cls.mymall.coupon.entity.SkuLadderEntity;
import com.cls.mymall.coupon.entity.SpuBoundsEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mall-coupon")
public interface CouponFeignService {

    @RequestMapping("coupon/spubounds/save")
    R save(@RequestBody SpuBoundsEntity spuBounds);

    @RequestMapping("coupon/skuladder/save")
    R save(@RequestBody SkuLadderEntity skuLadder);

    @RequestMapping("coupon/skufullreduction/save")
    R save(@RequestBody SkuFullReductionEntity skuFullReduction);

    @RequestMapping("coupon/memberprice/save")
    R save(@RequestBody MemberPriceEntity memberPrice);

}
