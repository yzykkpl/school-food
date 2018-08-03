package com.yzy.canteen.controller;

import com.thoughtworks.xstream.mapper.Mapper;
import com.yzy.canteen.service.MealPayService;
import com.yzy.canteen.utils.payUtil.model.PayResponse;
import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.dto.OrderDTO;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.service.OrderService;
import com.yzy.canteen.service.PayService;
import com.yzy.canteen.utils.ResultVOUtil;
import com.yzy.canteen.viewobject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description: 微信支付
 * @author: yzy
 * @create: 2018-04-04 16:23
 */
@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MealPayService mealPayService;
    /**
     * 生成预付订单
     *
     * @param:
     */
    @GetMapping("/create")
    public ResultVO<PayResponse> create(@RequestParam String orderId){
        //1.查询订单
        OrderDTO orderDTO = orderService.findById(orderId);
        if(orderDTO==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.发起支付
        PayResponse payResponse = payService.create(orderDTO);

        return ResultVOUtil.success(payResponse);
    }

    @RequestMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){

        log.info("回调XML={}",notifyData);
        PayResponse result2=mealPayService.notify(notifyData);;
        PayResponse result1=payService.notify(notifyData);

        if(result2==null&&result1==null) return null;
        return new ModelAndView("pay/success");

    }

}
