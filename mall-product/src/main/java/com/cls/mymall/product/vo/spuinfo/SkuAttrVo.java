package com.cls.mymall.product.vo.spuinfo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SkuAttrVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性名
     */
    private String attrName;
    /**
     * 属性值
     */
    private String attrValue;

}
