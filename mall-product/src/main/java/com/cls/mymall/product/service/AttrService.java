package com.cls.mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.product.entity.AttrEntity;
import com.cls.mymall.product.entity.ProductAttrValueEntity;
import com.cls.mymall.product.vo.AttrInfoRespVo;
import com.cls.mymall.product.vo.AttrUpdateRespVo;
import com.cls.mymall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 11:04:15
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttrVo(AttrVo attr);

    PageUtils saleOrBaseList(Map<String, Object> params, Long catId, String type);

    AttrInfoRespVo getInfoById(Long attrId);

    void updateAttr(AttrUpdateRespVo attr);

    List<ProductAttrValueEntity> listForSpu(Long spuId);
}

