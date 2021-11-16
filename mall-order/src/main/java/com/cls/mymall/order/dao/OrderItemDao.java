package com.cls.mymall.order.dao;

import com.cls.mymall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 14:19:47
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {

}
