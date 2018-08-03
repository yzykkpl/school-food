package com.yzy.canteen.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-28 12:25
 */
@Data
public class MealInfoForm {
    private String mealId;

    @NotEmpty(message = "名称必填")
    private String mealName;
    @NotNull(message = "价格必填")
    private BigDecimal mealPrice;

    private String  mealDescription;
    @NotEmpty(message = "图标必填")
    private String mealIcon;

    private String detailImage;
    @NotNull(message = "天数必填")
    private Integer days;

    @NotEmpty(message = "套餐所在月截止日期")
    private String mealDate;


    private Integer schoolId=-1;

}
