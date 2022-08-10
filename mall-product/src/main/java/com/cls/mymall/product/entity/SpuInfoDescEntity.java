package com.cls.mymall.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * spu信息介绍
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 11:04:15
 */
@Data
@TableName("pms_spu_info_desc")
public class SpuInfoDescEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(type = IdType.INPUT)
    private Long spuId;
    /**
     * 商品介绍
     */
    private String decript;

}
