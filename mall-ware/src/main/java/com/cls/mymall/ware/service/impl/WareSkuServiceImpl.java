package com.cls.mymall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.Query;
import com.cls.mymall.ware.dao.WareSkuDao;
import com.cls.mymall.ware.entity.WareSkuEntity;
import com.cls.mymall.ware.service.WareSkuService;
import com.cls.mymall.ware.vo.SkuHasStockVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
        Object wareId = params.get("wareId");
        Object skuId = params.get("skuId");
        wrapper.lambda().eq(!ObjectUtils.isEmpty(wareId), WareSkuEntity::getWareId, wareId)
                .eq(!ObjectUtils.isEmpty(skuId), WareSkuEntity::getSkuId, skuId);

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuHasStockVo> hasStock(List<Long> skuIds) {
        return baseMapper.hasStock(skuIds);
    }

}