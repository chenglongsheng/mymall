package com.cls.mymall.product;

import com.cls.mymall.product.entity.BrandEntity;
import com.cls.mymall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    void testRedisson() {
        System.out.println(redissonClient);
    }

    @Test
    void contextLoads() {

        BrandEntity brandEntity = new BrandEntity();

        brandEntity.setName("小米");
        brandService.save(brandEntity);

    }

}
