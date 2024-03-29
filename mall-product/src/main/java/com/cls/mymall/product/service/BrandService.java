package com.cls.mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 11:04:15
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetails(BrandEntity brand);
}

