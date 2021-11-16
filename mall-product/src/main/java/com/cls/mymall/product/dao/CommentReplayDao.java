package com.cls.mymall.product.dao;

import com.cls.mymall.product.entity.CommentReplayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价回复关系
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 11:04:14
 */
@Mapper
public interface CommentReplayDao extends BaseMapper<CommentReplayEntity> {

}
