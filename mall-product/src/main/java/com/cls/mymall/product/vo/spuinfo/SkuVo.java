package com.cls.mymall.product.vo.spuinfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SkuVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<SkuAttrVo> attr;

    private String skuName;

    private BigDecimal price;

    private String skuTitle;

    private String skuSubtitle;

    private List<ImageVo> images;

    private List<String> descar;

    private Integer fullCount;

    private BigDecimal discount;

    private Integer countStatus;

    private BigDecimal fullPrice;

    private BigDecimal reducePrice;

    private Integer priceStatus;

    private List<MemberPriceVo> memberPrice;

}
