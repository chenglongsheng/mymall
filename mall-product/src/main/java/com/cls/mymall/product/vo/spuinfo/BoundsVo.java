package com.cls.mymall.product.vo.spuinfo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BoundsVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 金币
     */
    private Integer buyBounds;
    /**
     * 成长值
     */
    private Integer growBounds;

}
