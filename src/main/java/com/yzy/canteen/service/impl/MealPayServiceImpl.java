package com.yzy.canteen.service.impl;

import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.dataobject.Refund;
import com.yzy.canteen.dto.MealDTO;
import com.yzy.canteen.service.MealPayService;
import com.yzy.canteen.service.MealService;
import com.yzy.canteen.utils.JsonUtil;
import com.yzy.canteen.utils.MathUtil;
import com.yzy.canteen.utils.payUtil.enums.BestPayTypeEnum;
import com.yzy.canteen.utils.payUtil.model.PayRequest;
import com.yzy.canteen.utils.payUtil.model.PayResponse;
import com.yzy.canteen.utils.payUtil.model.RefundRequest;
import com.yzy.canteen.utils.payUtil.model.RefundResponse;
import com.yzy.canteen.utils.payUtil.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-25 15:28
 */
@Service
@Slf4j
public class MealPayServiceImpl implements MealPayService{
    private static final String ORDER_NAME="套餐";
    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private MealService mealService;
    @Override
    public PayResponse create(MealDTO mealDTO) {
        PayRequest payRequest=new PayRequest();
        payRequest.setOpenid(mealDTO.getBuyerOpenid());
        payRequest.setOrderAmount(mealDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(mealDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("套餐微信支付 request={}", JsonUtil.toJson(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("套餐微信支付 response={}",JsonUtil.toJson(payResponse));

        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("微信支付异步通知，payResponse={}",JsonUtil.toJson(payResponse));
        //查询订单
        MealDTO mealDTO=null;
        try {
            mealDTO = mealService.findById(payResponse.getOrderId());
        }catch (SellException e){
            log.error("微信支付--套餐订单不存在，orderId={}",payResponse.getOrderId());
        }
        //判断是否存在
        if(mealDTO==null){
            log.error("微信支付--套餐订单不存在，orderId={}",payResponse.getOrderId());
            return null;
        }
        //判断金额是否一致
        if(!MathUtil.equals(mealDTO.getOrderAmount().doubleValue(),payResponse.getOrderAmount())) {
            log.error("微信支付，套餐金额不一致，orderId={},通知金额={}，系统金额={}", payResponse.getOrderId(), payResponse.getOrderAmount(), mealDTO.getOrderAmount());
            return null;
        }

        mealService.paid(mealDTO);
        return payResponse;
    }
    /**
     * 退款
     * @param mealDTO
     */
    @Override
    public RefundResponse refund(MealDTO mealDTO) {
//        RefundRequest refundRequest = new RefundRequest();
//        refundRequest.setOrderId(mealDTO.getOrderId());
//        refundRequest.setOrderAmount(mealDTO.getOrderAmount().doubleValue());
//        refundRequest.setRefundAmount(mealDTO.getOrderAmount().doubleValue());
//        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
//        log.info("【微信退款】request={}", JsonUtil.toJson(refundRequest));
//
//        RefundResponse refundResponse = bestPayService.refund(refundRequest);
//        log.info("【微信退款】response={}", JsonUtil.toJson(refundResponse));
//
//        return refundResponse;
        return null;

    }

    @Override
    public RefundResponse rebate(MealDTO mealDTO,Refund refund) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(mealDTO.getOrderId());
        refundRequest.setOrderAmount(mealDTO.getOrderAmount().doubleValue());
        refundRequest.setRefundAmount(refund.getPrice().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        refundRequest.setRefundId(refund.getRefundId());
        log.info("【微信退款】request={}", JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}", JsonUtil.toJson(refundResponse));

        return refundResponse;
    }


}
