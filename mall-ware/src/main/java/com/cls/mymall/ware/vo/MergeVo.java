package com.cls.mymall.ware.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MergeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long purchaseId;

    private List<Long> items;

}
