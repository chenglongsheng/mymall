package com.cls.mymall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.ware.entity.PurchaseDetailEntity;

import java.util.Map;

/**
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 14:28:03
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

