package com.cls.mymall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.Query;
import com.cls.mymall.product.dao.AttrDao;
import com.cls.mymall.product.entity.AttrAttrgroupRelationEntity;
import com.cls.mymall.product.entity.AttrEntity;
import com.cls.mymall.product.entity.CategoryEntity;
import com.cls.mymall.product.service.AttrAttrgroupRelationService;
import com.cls.mymall.product.service.AttrGroupService;
import com.cls.mymall.product.service.AttrService;
import com.cls.mymall.product.service.CategoryService;
import com.cls.mymall.product.vo.AttrInfoRespVo;
import com.cls.mymall.product.vo.AttrRespVo;
import com.cls.mymall.product.vo.AttrUpdateRespVo;
import com.cls.mymall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrGroupService attrGroupService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttrVo(AttrVo attr) {
        AttrEntity entity = new AttrEntity();
        BeanUtils.copyProperties(attr, entity);
        super.save(entity);

        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrId(entity.getAttrId());
        relationEntity.setAttrGroupId(attr.getAttrGroupId());
        attrAttrgroupRelationService.save(relationEntity);
    }

    @Override
    public PageUtils baseList(Map<String, Object> params, Long catId) {

        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(catId != 0, AttrEntity::getCatelogId, catId);

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((wrapper) -> wrapper.lambda().eq(AttrEntity::getAttrId, key).or().like(AttrEntity::getAttrName, key));
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);

        List<AttrEntity> list = page.getRecords();

        List<AttrRespVo> respVoList = list.stream().map(attrEntity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            AttrAttrgroupRelationEntity attrAttrgroupRelation = attrAttrgroupRelationService.getOne(Wrappers.lambdaQuery(AttrAttrgroupRelationEntity.class)
                    .eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId()));
            if (attrAttrgroupRelation != null) {
                Long groupId = attrAttrgroupRelation.getAttrGroupId();
                attrRespVo.setGroupName(attrGroupService.getById(groupId).getAttrGroupName());
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
        AttrAttrgroupRelationEntity relation = attrAttrgroupRelationService.getOne(Wrappers.lambdaQuery(AttrAttrgroupRelationEntity.class)
                .eq(AttrAttrgroupRelationEntity::getAttrId, attrId));
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

        Long attrGroupId = attr.getAttrGroupId();
        AttrAttrgroupRelationEntity attrAttrgroupRelation = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelation.setAttrGroupId(attrGroupId);
        attrAttrgroupRelationService.update(
                attrAttrgroupRelation,
                Wrappers.lambdaQuery(AttrAttrgroupRelationEntity.class)
                        .eq(AttrAttrgroupRelationEntity::getAttrId, attr.getAttrId()));
    }

}