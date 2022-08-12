package com.cls.mymall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.Query;
import com.cls.mymall.product.dao.SkuInfoDao;
import com.cls.mymall.product.entity.SkuInfoEntity;
import com.cls.mymall.product.service.SkuInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Object key = params.get("key");
        Object catelogId = params.get("catelogId");
        Object brandId = params.get("brandId");
        Object min = params.get("min");
        Object max = params.get("max");

        QueryWrapper<SkuInfoEntity> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(key)) {
            queryWrapper.eq("skuId", key).or().like("sku_name", key);
        }
        if (!ObjectUtils.isEmpty(catelogId)) {
            queryWrapper.eq("catalog_id", catelogId);
        }
        if (!ObjectUtils.isEmpty(brandId)) {
            queryWrapper.eq("brand_id", brandId);
        }
        if (!ObjectUtils.isEmpty(min)) {
            queryWrapper.ge("price", min);
        }
        if (!ObjectUtils.isEmpty(max)) {
            queryWrapper.le("price", max);
        }


        IPage<SkuInfoEntity> page = this.page(new Query<SkuInfoEntity>().getPage(params), queryWrapper);

        return new PageUtils(page);
    }

}