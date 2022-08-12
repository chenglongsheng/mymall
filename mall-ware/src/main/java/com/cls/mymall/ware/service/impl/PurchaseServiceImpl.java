package com.cls.mymall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.Query;
import com.cls.mymall.ware.dao.PurchaseDao;
import com.cls.mymall.ware.entity.PurchaseDetailEntity;
import com.cls.mymall.ware.entity.PurchaseEntity;
import com.cls.mymall.ware.service.PurchaseDetailService;
import com.cls.mymall.ware.service.PurchaseService;
import com.cls.mymall.ware.vo.MergeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(new Query<PurchaseEntity>().getPage(params), new QueryWrapper<PurchaseEntity>());

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnReceiveList(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(new Query<PurchaseEntity>().getPage(params), new QueryWrapper<PurchaseEntity>().eq("status", 0).or().eq("status", 1));

        return new PageUtils(page);
    }

    @Override
    public void merge(MergeVo vo) {
        Long purchaseId = vo.getPurchaseId();
        List<Long> items = vo.getItems();
        if (purchaseId == null) {
            // 新建采购单
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setPriority(1);
            purchaseEntity.setStatus(0);
            super.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }
        for (Long item : items) {
            PurchaseDetailEntity purchaseDetail = new PurchaseDetailEntity();
            purchaseDetail.setId(item);
            purchaseDetail.setStatus(1);
            purchaseDetail.setPurchaseId(purchaseId);
            purchaseDetailService.updateById(purchaseDetail);
        }
    }

}