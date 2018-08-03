package com.yzy.canteen.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @description: 退款申请表单
 * @author: yzy
 * @create: 2018-05-23 23:18
 */
@Data
public class RefundForm {
    @NotEmpty(message = "token必填")
    private String token;

    @NotEmpty(message = "orderId必填")
    private String orderId;

    @NotEmpty(message = "原因必填")
    private String reason;

    @NotEmpty(message = "日期必填")
    private String date;

    private Integer days;

}
