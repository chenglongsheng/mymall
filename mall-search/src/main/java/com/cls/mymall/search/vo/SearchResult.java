package com.cls.mymall.search.vo;

import com.cls.mymall.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
    /**
     * 所有商品信息
     */
    private List<SkuEsModel> products;
    /**
     * 当前页码数
     */
    private Integer pageNum;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 总页码
     */
    private Integer totalPages;
    /**
     * 所有涉及到的品牌
     */
    private List<BrandVo> brands;
    /**
     * 所有涉及到的属性
     */
    private List<AttrVo> attrs;
    /**
     * 所有涉及到的分类
     */
    private List<CatalogVo> catalogs;


    @Data
    static class BrandVo {
        private Long brandId;
        private String brandName;
        private String brandImg;
    }

    @Data
    static class AttrVo {
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }

    @Data
    static class CatalogVo {
        private Long catalogId;
        private String catalogName;
    }

}
