package com.cls.mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.product.entity.CategoryEntity;
import com.cls.mymall.product.vo.CatalogLevel2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 11:04:15
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listTree();

    void removeMenusByIds(List<Long> asList);

    Long[] getAttrGroupPath(Long catelogId);

    void updateDetails(CategoryEntity category);

    List<CategoryEntity> getLevel1Categories();

    Map<String, List<CatalogLevel2Vo>> getCatalogJson();
}

