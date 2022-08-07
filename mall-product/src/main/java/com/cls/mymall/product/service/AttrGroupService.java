package com.cls.mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.product.entity.AttrEntity;
import com.cls.mymall.product.entity.AttrGroupEntity;
import com.cls.mymall.product.vo.AttrRelationDeleteVo;
import com.cls.mymall.product.vo.WithAttrRespVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 11:04:15
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    AttrGroupEntity getAttrGroupInfo(Long attrGroupId);

    List<AttrEntity> attrRelationList(Long attrgroupId);

    PageUtils noAttrRelation(Map<String, Object> params, Long attrgroupId);

    void attrRelationDelete(List<AttrRelationDeleteVo> ids);

    void attrRelation(List<AttrRelationDeleteVo> ids);

    List<WithAttrRespVo> withAttr(Long catelogId);
}

