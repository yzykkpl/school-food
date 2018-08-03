package com.yzy.canteen.service.impl;

import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.utils.payUtil.enums.BestPayTypeEnum;
import com.yzy.canteen.utils.payUtil.model.PayRequest;
import com.yzy.canteen.utils.payUtil.model.PayResponse;
import com.yzy.canteen.utils.payUtil.model.RefundRequest;
import com.yzy.canteen.utils.payUtil.model.RefundResponse;
import com.yzy.canteen.dto.OrderDTO;
import com.yzy.canteen.service.OrderService;
import com.yzy.canteen.service.PayService;
import com.yzy.canteen.utils.JsonUtil;
import com.yzy.canteen.utils.MathUtil;
import com.yzy.canteen.utils.payUtil.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: yzy
 * @create: 2018-04-04 20:20
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME = "水果/零食";

    @Autowired
    private OrderService orderService;
    @Autowired
    private BestPayServiceImpl bestPayService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("微信支付 request={}", JsonUtil.toJson(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("微信支付 response={}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("微信支付异步通知，payResponse={}", JsonUtil.toJson(payResponse));
        //查询订单
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findById(payResponse.getOrderId());
        } catch (SellException e) {
            log.error(e.getMessage());
        }

        //判断是否存在
        if (orderDTO == null) {
            log.error("水果订单不存在，orderId={}", payResponse.getOrderId());
            return null;
        }
        //判断金额是否一致
        if (!MathUtil.equals(orderDTO.getOrderAmount().doubleValue(), payResponse.getOrderAmount())) {
            log.error("微信支付，水果订单金额不一致，orderId={},通知金额={}，系统金额={}", payResponse.getOrderId(), payResponse.getOrderAmount(), orderDTO.getOrderAmount());
            return null;
        }

        orderService.paid(orderDTO);
        return payResponse;
    }

    /**
     * 退款
     *
     * @param orderDTO
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setRefundAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】request={}", JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}", JsonUtil.toJson(refundResponse));

        return refundResponse;
    }


}
