package com.yzy.canteen.service;

import com.yzy.canteen.dto.OrderDTO;

/**
 * @description: 买家查询和取消订单因为涉及检验openid和orderId是否匹配，所以单独处理
 * @author: yzy
 * @create: 2018-03-26 22:46
 */
public interface BuyerService {
    //查询一个订单
    OrderDTO findOrderOne(String openid,String orderId);
    //申请退款
    OrderDTO refundOrderOne(String openid,String orderId);
    //取消订单
    OrderDTO cancelOrderOne(String openid,String orderId);
    //token换openid
    String getOpenid(String token);
}
