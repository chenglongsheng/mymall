package com.cls.mymall.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrRelationDeleteVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性分组id
     */
    private Long attrGroupId;

}
