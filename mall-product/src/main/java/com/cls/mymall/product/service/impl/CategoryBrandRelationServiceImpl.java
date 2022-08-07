package com.cls.mymall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.Query;
import com.cls.mymall.product.dao.CategoryBrandRelationDao;
import com.cls.mymall.product.entity.CategoryBrandRelationEntity;
import com.cls.mymall.product.service.BrandService;
import com.cls.mymall.product.service.CategoryBrandRelationService;
import com.cls.mymall.product.service.CategoryService;
import com.cls.mymall.product.vo.CatBrandRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryBrandRelationEntity> relationList(Long brandId) {
        return super.list(Wrappers.lambdaQuery(CategoryBrandRelationEntity.class)
                .eq(CategoryBrandRelationEntity::getBrandId, brandId));
    }

    @Override
    public void saveInfo(CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelation.setCatelogName(categoryService.getById(categoryBrandRelation.getCatelogId()).getName());
        categoryBrandRelation.setBrandName(brandService.getById(categoryBrandRelation.getBrandId()).getName());
        super.save(categoryBrandRelation);
    }

    @Override
    public void updateDetails(Long brandId, String name) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setBrandId(brandId);
        entity.setBrandName(name);
        super.update(entity, Wrappers.lambdaQuery(CategoryBrandRelationEntity.class)
                .eq(CategoryBrandRelationEntity::getBrandId, brandId));
    }

    @Override
    public void updateCascade(Long catId, String name) {
        baseMapper.updateCascade(catId, name);
    }

    @Override
    public List<CatBrandRelationVo> brandsList(Long catId) {
        List<CategoryBrandRelationEntity> list = super.list(Wrappers.lambdaQuery(CategoryBrandRelationEntity.class)
                .eq(CategoryBrandRelationEntity::getCatelogId, catId));
        List<CatBrandRelationVo> resp = new ArrayList<>();
        for (CategoryBrandRelationEntity relation : list) {
            CatBrandRelationVo vo = new CatBrandRelationVo();
            vo.setBrandId(relation.getBrandId());
            vo.setBrandName(relation.getBrandName());
            resp.add(vo);
        }
        return resp;
    }

}