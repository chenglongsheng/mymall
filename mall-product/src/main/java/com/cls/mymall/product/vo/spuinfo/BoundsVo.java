package com.cls.mymall.product.vo.spuinfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BoundsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 金币
     */
    private BigDecimal buyBounds;
    /**
     * 成长值
     */
    private BigDecimal growBounds;

}
