package com.cls.mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.product.entity.CategoryBrandRelationEntity;
import com.cls.mymall.product.vo.CatBrandRelationVo;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 11:04:15
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryBrandRelationEntity> relationList(Long brandId);

    void saveInfo(CategoryBrandRelationEntity categoryBrandRelation);

    void updateDetails(Long brandId, String name);

    void updateCascade(Long catId, String name);

    List<CatBrandRelationVo> brandsList(Long catId);
}

