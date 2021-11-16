package com.cls.mymall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.member.entity.MemberCollectSubjectEntity;

import java.util.Map;

/**
 * 会员收藏的专题活动
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 14:13:17
 */
public interface MemberCollectSubjectService extends IService<MemberCollectSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

