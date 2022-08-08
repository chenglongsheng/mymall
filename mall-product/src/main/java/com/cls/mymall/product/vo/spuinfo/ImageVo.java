package com.cls.mymall.product.vo.spuinfo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String imgUrl;

    private Integer defaultImg;

}
