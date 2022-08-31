package com.cls.mymall.search.vo;

import lombok.Data;

import java.util.List;

@Data
public class SearchParam {

    /**
     * 页面的全文检索匹配关键字
     */
    private String keyword;
    /**
     * 三级分类id
     */
    private Long catalog3Id;
    /**
     * sort=saleCount_asc/desc
     * sort=skuPrice_asc/desc
     * sort=hotScore_asc/desc
     * 排序条件
     */
    private String sort;
    /**
     * 过滤条件
     * hasStock、skuPrice区间、brandId、catalog3Id、attrs
     * hasStock=0/1
     * skuPrice=1_500/_500/500_
     */
    private Integer hasStock;

    private String skuPrice;

    private List<Long> brandId;

    private List<String> attrs;
    /**
     * 页码
     */
    private Integer pageNum;

}
