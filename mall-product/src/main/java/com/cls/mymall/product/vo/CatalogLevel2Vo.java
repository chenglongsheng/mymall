package com.cls.mymall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogLevel2Vo {

    private String catalog1Id;

    private List<CatalogLevel3Vo> catalog3List;

    private String id;

    private String name;

}
