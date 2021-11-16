package com.cls.mymall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.member.entity.MemberLoginLogEntity;

import java.util.Map;

/**
 * 会员登录记录
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 14:13:17
 */
public interface MemberLoginLogService extends IService<MemberLoginLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

