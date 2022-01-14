package com.cls.mymall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cls.mymall.common.valid.AddGroup;
import com.cls.mymall.common.valid.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 11:04:15
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @NotNull(groups = UpdateGroup.class, message = "修改必须指定品牌ID")
    @Null(groups = AddGroup.class, message = "新增无需须指定品牌ID")
    @TableId
    private Long brandId;
    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名不能为空", groups = AddGroup.class)
    private String name;
    /**
     * 品牌logo地址
     */
    @URL(message = "需要合法的url地址", groups = {AddGroup.class, UpdateGroup.class})
    @NotBlank(message = "品牌logo不能为空", groups = AddGroup.class)
    private String logo;
    /**
     * 介绍
     */
    private String description;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @NotNull(message = "状态不能为空", groups = AddGroup.class)
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @NotEmpty(groups = AddGroup.class, message = "检索首字母不能为空")
    @Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是一个字母", groups = {AddGroup.class, UpdateGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @Min(value = 0, message = "排序字段必须是一个正整数", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "排序字段不能为空", groups = AddGroup.class)
    private Integer sort;

}
