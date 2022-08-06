package com.cls.mymall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.Query;
import com.cls.mymall.product.dao.BrandDao;
import com.cls.mymall.product.entity.BrandEntity;
import com.cls.mymall.product.service.BrandService;
import com.cls.mymall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                new QueryWrapper<BrandEntity>()
                        .lambda()
                        .eq(!StringUtils.isEmpty(key), BrandEntity::getBrandId, key)
                        .or()
                        .like(!StringUtils.isEmpty(key), BrandEntity::getName, key)
        );

        return new PageUtils(page);
    }

    @Override
    public void updateDetails(BrandEntity brand) {
        super.updateById(brand);
        if (!StringUtils.isEmpty(brand.getName())) {
            categoryBrandRelationService.updateDetails(brand.getBrandId(), brand.getName());
        }
    }

}