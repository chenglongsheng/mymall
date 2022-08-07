package com.cls.mymall.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long attrGroupId;// 1
    private String attrName;//: "上市日期"
    private Integer attrType;//: 1
    private Long catelogId;//: 225
    private Long enable;//: 1
    private String icon;//: "xxx"
    private Integer searchType;//: 0
    private Integer showDesc;//: 0
    private String valueSelect;//: "2021-12-26"
    private Integer valueType;//: 0
}
