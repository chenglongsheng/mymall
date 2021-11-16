package com.cls.mymall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 14:13:17
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

