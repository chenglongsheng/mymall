package com.cls.mymall.product.vo.spuinfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SpuInfoSaveVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品名称
     */
    private String spuName;
    /**
     * 商品描述
     */
    private String spuDescription;
    /**
     * 分类id
     */
    private Long catalogId;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 重量
     */
    private BigDecimal weight;
    /**
     * 上架状态[0 - 下架，1 - 上架]
     */
    private Integer publishStatus;
    /**
     * 商品介绍图片地址
     */
    private List<String> decript;
    /**
     * spu图片地址
     */
    private List<String> images;
    /**
     * 积分
     */
    private BoundsVo bounds;
    /**
     * 基本属性
     */
    List<BaseAttr> baseAttrs;
    /**
     *
     */
    private List<SkuVo> skus;


}
