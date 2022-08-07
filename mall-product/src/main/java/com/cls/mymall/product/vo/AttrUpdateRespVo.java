package com.cls.mymall.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrUpdateRespVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性分组id
     */
    private Long attrGroupId;
    /**
     * 属性名
     */
    private String attrName;
    /**
     * 属性类型
     */
    private Integer attrType;
    /**
     * 分类id
     */
    private Long catelogId;
    /**
     * 是否可用
     */
    private Integer enable;
    /**
     * 图标
     */
    private String icon;
    /**
     * 是否检索
     */
    private Integer searchType;
    /**
     * 快速展示
     */
    private Integer showDesc;
    /**
     * 可选值列表
     */
    private String valueSelect;
    /**
     * 可选值模式
     */
    private Integer valueType;
}
