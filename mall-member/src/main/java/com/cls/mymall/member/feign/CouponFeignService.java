package com.cls.mymall.member.feign;

import com.cls.mymall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mymall-coupon")
public interface CouponFeignService {

    @RequestMapping("/coupon/coupon/testcoupons")
    R coupons();

}
