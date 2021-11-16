package com.cls.mymall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.member.entity.MemberCollectSpuEntity;

import java.util.Map;

/**
 * 会员收藏的商品
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 14:13:17
 */
public interface MemberCollectSpuService extends IService<MemberCollectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

