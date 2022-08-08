package com.cls.mymall.product.vo.spuinfo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseAttr implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 可选值列表[用逗号分隔]
     */
    private String attrValues;
    /**
     * 快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整
     */
    private Integer showDesc;

}
