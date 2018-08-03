package com.yzy.canteen.utils.payUtil.model;

import com.yzy.canteen.utils.payUtil.enums.BestPayTypeEnum;
import lombok.Data;

/**
 * 支付时请求参数
 */
@Data
public class RefundRequest {

    /**
     * 支付方式.
     */
    private BestPayTypeEnum payTypeEnum;

    /**
     * 订单号.
     */
    private String orderId;

    /**
     * 订单金额.
     */
    private Double orderAmount;
    /**
     * 退款金额.
     */
    private Double refundAmount;

    private String refundId;
}
