package com.cls.mymall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cls.mymall.common.constant.ProductConstant;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.Query;
import com.cls.mymall.product.dao.AttrDao;
import com.cls.mymall.product.entity.AttrAttrgroupRelationEntity;
import com.cls.mymall.product.entity.AttrEntity;
import com.cls.mymall.product.entity.CategoryEntity;
import com.cls.mymall.product.entity.ProductAttrValueEntity;
import com.cls.mymall.product.service.*;
import com.cls.mymall.product.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), new QueryWrapper<>());
        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttrVo(AttrVo attr) {
        AttrEntity entity = new AttrEntity();
        BeanUtils.copyProperties(attr, entity);
        super.save(entity);
        // 基本属性保存关联关系
        if (Objects.equals(entity.getAttrType(), ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrId(entity.getAttrId());
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationService.save(relationEntity);
        }
    }

    @Override
    public PageUtils saleOrBaseList(Map<String, Object> params, Long catId, String type) {

        // 判断基本属性还是销售属性
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type", type.equals("base") ? ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        // 判断是否带有分类id
        queryWrapper.lambda().eq(catId != 0, AttrEntity::getCatelogId, catId);
        // 模糊查询
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((wrapper) -> wrapper.lambda().eq(AttrEntity::getAttrId, key).or().like(AttrEntity::getAttrName, key));
        }
        IPage<AttrEntity> page = page(new Query<AttrEntity>().getPage(params), queryWrapper);
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> list = page.getRecords();
        // 属性所属的分组分类封装
        List<AttrRespVo> respVoList = list.stream().map(attrEntity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            if (type.equals("base")) {
                AttrAttrgroupRelationEntity attrAttrgroupRelation = attrAttrgroupRelationService.getOne(Wrappers.lambdaQuery(AttrAttrgroupRelationEntity.class).eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId()));
                if (attrAttrgroupRelation.getAttrGroupId() != null) {
                    attrRespVo.setGroupName(attrGroupService.getById(attrAttrgroupRelation.getAttrGroupId()).getAttrGroupName());
                }
            }
            CategoryEntity category = categoryService.getById(attrEntity.getCatelogId());
            if (category != null) {
                attrRespVo.setCatelogName(category.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(respVoList);
        return pageUtils;
    }

    @Override
    public AttrInfoRespVo getInfoById(Long attrId) {
        AttrEntity attr = super.getById(attrId);
        AttrInfoRespVo respVo = new AttrInfoRespVo();
        BeanUtils.copyProperties(attr, respVo);
        AttrAttrgroupRelationEntity relation = attrAttrgroupRelationService.getOne(Wrappers.lambdaQuery(AttrAttrgroupRelationEntity.class).eq(AttrAttrgroupRelationEntity::getAttrId, attrId));
        if (relation != null) {
            respVo.setAttrGroupId(relation.getAttrGroupId());
        }
        respVo.setCatelogPath(categoryService.getAttrGroupPath(attr.getCatelogId()));
        return respVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrUpdateRespVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        super.updateById(attrEntity);
        if (attr.getAttrType().equals(ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode())) {
            Long attrGroupId = attr.getAttrGroupId();
            AttrAttrgroupRelationEntity attrAttrgroupRelation = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelation.setAttrGroupId(attrGroupId);
            attrAttrgroupRelationService.update(attrAttrgroupRelation, Wrappers.lambdaQuery(AttrAttrgroupRelationEntity.class).eq(AttrAttrgroupRelationEntity::getAttrId, attr.getAttrId()));
        }
    }

    @Override
    public List<ProductAttrValueEntity> listForSpu(Long spuId) {
        return productAttrValueService.list(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
    }

    @Override
    @Transactional
    public void updateSpu(Long spuId, List<UpdateSpuAttrVo> vo) {
        for (UpdateSpuAttrVo updateSpuAttrVo : vo) {
            ProductAttrValueEntity entity = new ProductAttrValueEntity();
            entity.setAttrName(updateSpuAttrVo.getAttrName());
            entity.setAttrValue(updateSpuAttrVo.getAttrValue());
            entity.setQuickShow(updateSpuAttrVo.getQuickShow());
            productAttrValueService.update(entity, new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId).eq("attr_id", updateSpuAttrVo.getAttrId()));
            AttrEntity attrEntity = new AttrEntity();
            attrEntity.setAttrId(updateSpuAttrVo.getAttrId());
            attrEntity.setAttrName(updateSpuAttrVo.getAttrName());
            attrEntity.setValueSelect(updateSpuAttrVo.getAttrValue());
            attrEntity.setShowDesc(updateSpuAttrVo.getQuickShow());
            super.updateById(attrEntity);
        }
    }

}