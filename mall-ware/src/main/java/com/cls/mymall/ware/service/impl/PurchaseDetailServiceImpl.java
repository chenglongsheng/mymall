package com.cls.mymall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.Query;
import com.cls.mymall.ware.dao.PurchaseDetailDao;
import com.cls.mymall.ware.entity.PurchaseDetailEntity;
import com.cls.mymall.ware.service.PurchaseDetailService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseDetailEntity> wrapper = new QueryWrapper<>();
        Object key = params.get("key");
        Object status = params.get("status");
        Object wareId = params.get("wareId");
        wrapper.lambda().eq(!ObjectUtils.isEmpty(key), PurchaseDetailEntity::getId, key).eq(!ObjectUtils.isEmpty(status), PurchaseDetailEntity::getStatus, status).eq(!ObjectUtils.isEmpty(wareId), PurchaseDetailEntity::getWareId, wareId);
        IPage<PurchaseDetailEntity> page = this.page(new Query<PurchaseDetailEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

}