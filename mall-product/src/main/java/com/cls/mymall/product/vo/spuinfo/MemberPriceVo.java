package com.cls.mymall.product.vo.spuinfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MemberPriceVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private BigDecimal price;

}
