package com.yzy.canteen.service.impl;

import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.constant.TokenConstant;
import com.yzy.canteen.dto.OrderDTO;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.service.BuyerService;
import com.yzy.canteen.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: yzy
 * @create: 2018-03-26 22:49
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private OrderService orderService;
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {

        return checkOrderOwner(openid,orderId);
    }
    //用户暂不可自动退款
    @Override
    public OrderDTO refundOrderOne(String openid, String orderId) {
        OrderDTO orderDTO=checkOrderOwner(openid,orderId);
        if(orderDTO==null){
            log.error("取消订单，查不到该订单，orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.applyForRefund(orderDTO);
    }

    @Override
    public OrderDTO cancelOrderOne(String openid, String orderId) {
        OrderDTO orderDTO=checkOrderOwner(openid,orderId);
        if(orderDTO==null){
            log.error("取消订单，查不到该订单，orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.userCancel(orderDTO);
    }

    @Override
    public String getOpenid(String token) {
        String key=String.format(TokenConstant.TOKEN_PREFIX,token);
        String openid=redisTemplate.opsForValue().get(key);
        if(openid==null) return "e";

        return openid;
    }

    private OrderDTO checkOrderOwner(String openid, String orderId){
        OrderDTO orderDTO=orderService.findById(orderId);
        if(orderDTO==null){
            return null;
        }
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("查询订单,买家的订单不匹配，openid={},orderId={}",openid,orderId);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
