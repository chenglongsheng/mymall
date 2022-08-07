package com.cls.mymall.product.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatBrandRelationVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 品牌名称
     */
    private String brandName;
}
