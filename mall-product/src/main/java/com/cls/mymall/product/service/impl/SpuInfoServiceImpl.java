package com.cls.mymall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.Query;
import com.cls.mymall.product.dao.SpuInfoDao;
import com.cls.mymall.product.entity.SpuInfoEntity;
import com.cls.mymall.product.service.BrandService;
import com.cls.mymall.product.service.CategoryService;
import com.cls.mymall.product.service.SpuInfoService;
import com.cls.mymall.product.vo.SpuInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String key = (String) params.get("key");
        Long catelogId = (Long) params.get("catelogId");
        Long brandId = (Long) params.get("brandId");
        Integer status = (Integer) params.get("status");

        QueryWrapper<SpuInfoEntity> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(key)) {
            queryWrapper.eq("id", key).or().like("spu_name", key).or().like("spu_description", key);
        }
        if (catelogId != null) {
            queryWrapper.eq("catalog_id", catelogId);
        }
        if (brandId != null) {
            queryWrapper.eq("brand_id", brandId);
        }
        if (status != null) {
            queryWrapper.eq("publish_status", status);
        }

        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                queryWrapper
        );

        List<SpuInfoEntity> records = page.getRecords();
        List<SpuInfoVo> collect = records.stream().map(item -> {
            SpuInfoVo vo = new SpuInfoVo();
            BeanUtils.copyProperties(item, vo);
            if (item.getBrandId() != null) {
                vo.setBrandName(brandService.getById(item.getBrandId()).getName());
            }
            if (item.getCatalogId() != null) {
                vo.setCatalogName(categoryService.getById(item.getCatalogId()).getName());
            }
            return vo;
        }).collect(Collectors.toList());

        return new PageUtils(collect, (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

}