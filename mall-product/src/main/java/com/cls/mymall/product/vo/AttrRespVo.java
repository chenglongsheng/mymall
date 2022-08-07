package com.cls.mymall.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrRespVo implements Serializable {
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
     * 属性类型，0-销售属性，1-基本属性
     */
    private Integer attrType;
    /**
     * 所属分类名字
     */
    private String catelogName;
    /**
     * 所属分组名字
     */
    private String groupName;
    /**
     * 是否启用
     */
    private Long enable;
    /**
     * 图标
     */
    private String icon;
    /**
     * 是否需要检索[0-不需要，1-需要]
     */
    private Integer searchType;
    /**
     * 是否展示在介绍上；0-否 1-是
     */
    private Integer showDesc;
    /**
     * 可选值列表[用逗号分隔]
     */
    private String valueSelect;
    /**
     * 值类型[0-为单个值，1-可以选择多个值]
     */
    private Integer valueType;
}
