package com.yzy.canteen.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: yzy
 * @create: 2018-04-01 15:11
 */
@Data
public class CategoryForm {
    private Integer categoryId;
    @NotEmpty(message = "名称必填")
    private String categoryName;

    @NotNull(message = "类型编号必填")
    private Integer categoryType;

    private String categoryIcon;
}
