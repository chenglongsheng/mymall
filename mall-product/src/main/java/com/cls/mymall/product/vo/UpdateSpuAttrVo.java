package com.cls.mymall.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateSpuAttrVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long attrId;

    private String attrName;

    private String attrValue;

    private Integer quickShow;

}
