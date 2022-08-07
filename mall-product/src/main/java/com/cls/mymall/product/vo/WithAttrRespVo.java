package com.cls.mymall.product.vo;

import com.cls.mymall.product.entity.AttrEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WithAttrRespVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long attrGroupId;

    private String attrGroupName;

    private Integer sort;

    private String descript;

    private String icon;

    private Long catelogId;

    private List<AttrEntity> attrs;

}
