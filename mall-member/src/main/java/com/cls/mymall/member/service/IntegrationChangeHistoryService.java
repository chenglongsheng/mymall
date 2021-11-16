package com.cls.mymall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 积分变化历史记录
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 14:13:17
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

