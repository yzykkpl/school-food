package com.yzy.canteen.controller;

import com.yzy.canteen.repository.OrderMealRepository;
import com.yzy.canteen.repository.UserInfoRepository;
import com.yzy.canteen.service.OrderService;
import com.yzy.canteen.service.PayService;
import com.yzy.canteen.utils.JsonUtil;
import com.yzy.canteen.utils.payUtil.model.RefundResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-19 22:53
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    OrderService orderService;
    @Autowired
    PayService payService;
    @Autowired
    OrderMealRepository orderMealRepository;
    @Autowired
    UserInfoRepository userInfoRepository;

    @RequestMapping("/")
    public ModelAndView jsonTest(){
        return new ModelAndView("redirect:/seller/mealOrder/list");

    }

}
