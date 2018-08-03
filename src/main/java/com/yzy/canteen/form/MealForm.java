package com.yzy.canteen.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-23 21:44
 */
@Data
public class MealForm {

    @NotEmpty(message = "姓名必填")
    private String buyerName;

    @NotEmpty(message = "手机必填")
    private String buyerPhone;

    @NotEmpty(message = "学号必填")
    private String stdNum;

    @NotEmpty(message = "学校必填")
    private String buyerSchool;
    @NotEmpty(message = "班级必填")
    private String buyerCls;

    @NotEmpty(message = "套餐id必填")
    private String mealId;

    @NotEmpty(message = "token必填")
    private String token;

    //备注
    private String comment;

    private String buyerOpenid;
}
