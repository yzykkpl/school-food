package com.yzy.canteen.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * @description: 订单表单
 * @author: yzy
 * @create: 2018-03-26 18:55
 */
@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    private String buyerName;

    @NotEmpty(message = "手机必填")
    private String buyerPhone;

    @NotEmpty(message = "学校必填")
    private String buyerSchool;
    @NotEmpty(message = "班级必填")
    private String buyerCls;

    @NotEmpty(message = "学号必填")
    private String stdNum;

    @NotEmpty(message = "商品id")
    private String productId;

//    @NotEmpty(message = "起始日期不能为空")
//    private String startDate;
//    @NotEmpty(message = "起始日期不能为空")
//    private String endDate;

    @NotEmpty(message = "日期不能为空")
    private String date;

    @NotEmpty(message = "token必填")
    private String token;

    @NotNull(message = "数量必填")
    private Integer counts;

    //备注
    private String comment;

    private String buyerOpenid;
}
