package com.cls.mymall.order.dao;

import com.cls.mymall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 14:19:47
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

}
