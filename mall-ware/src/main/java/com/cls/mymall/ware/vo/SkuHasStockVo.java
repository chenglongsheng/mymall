package com.cls.mymall.ware.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SkuHasStockVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long skuId;
    private Integer stock;
}
