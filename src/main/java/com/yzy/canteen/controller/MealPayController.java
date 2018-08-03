package com.yzy.canteen.controller;

import com.yzy.canteen.dto.MealDTO;
import com.yzy.canteen.service.MealPayService;
import com.yzy.canteen.service.MealService;
import com.yzy.canteen.utils.payUtil.model.PayResponse;
import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.utils.ResultVOUtil;
import com.yzy.canteen.viewobject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description: 套餐微信支付
 * @author: yzy
 * @create: 2018-05-31 16:23
 */
@RestController
@RequestMapping("/mealPay")
@Slf4j
public class MealPayController {
    @Autowired
    private MealService mealService;

    @Autowired
    private MealPayService mealPayService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 生成预付订单
     *
     * @param:
     */
    @GetMapping("/create")
    public ResultVO<PayResponse> create(@RequestParam String orderId){
        //1.查询订单
        MealDTO mealDTO = mealService.findById(orderId);
        if(mealDTO==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.发起支付
        PayResponse payResponse = mealPayService.create(mealDTO);

        return ResultVOUtil.success(payResponse);
    }

    @RequestMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){

        //log.info("回调XML={}",notifyData);

        mealPayService.notify(notifyData);

        return new ModelAndView("pay/success");

    }

}
