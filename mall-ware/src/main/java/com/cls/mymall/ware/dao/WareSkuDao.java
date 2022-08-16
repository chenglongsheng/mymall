package com.cls.mymall.ware.dao;

import com.cls.mymall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cls.mymall.ware.vo.SkuHasStockVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库存
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 14:28:02
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    List<SkuHasStockVo> hasStock(@Param("skuIds") List<Long> skuIds);
}
