package com.yzy.canteen.controller;

import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.dataobject.MealInfo;
import com.yzy.canteen.dataobject.OrderMeal;
import com.yzy.canteen.dataobject.Refund;
import com.yzy.canteen.dto.MealDTO;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.form.MealForm;
import com.yzy.canteen.form.RefundForm;
import com.yzy.canteen.repository.OrderMealRepository;
import com.yzy.canteen.repository.RefundRepository;
import com.yzy.canteen.service.BuyerService;
import com.yzy.canteen.service.MealInfoService;
import com.yzy.canteen.service.MealService;
import com.yzy.canteen.utils.*;
import com.yzy.canteen.viewobject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * @description:用户套餐订单管理
 * @author: yzy
 * @create: 2018-05-23 21:42
 */
@RestController
@RequestMapping("/buyer/meal/order")
@Slf4j
public class BuyerMealController {
    @Autowired
    MealService mealService;
    @Autowired
    BuyerService buyerService;
    @Autowired
    MealInfoService mealInfoService;
    @Autowired
    RefundRepository refundRepository;
    @Autowired
    OrderMealRepository mealRepository;


    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid MealForm mealForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("创建套餐订单，参数不正确，mealForm={}",mealForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }
        String openid=buyerService.getOpenid(mealForm.getToken());
        if(openid.equals("e")){
            return ResultVOUtil.error(-1,"relogin");
        }
        mealForm.setBuyerOpenid(openid);
        OrderMeal orderMeal=new OrderMeal();

        String mealId=mealForm.getMealId();
        List<OrderMeal> oldList = mealRepository.findByBuyerOpenidAndPayStatus(openid,1);
        for(OrderMeal order:oldList){
            if(order.getMealId().equals(mealId)){
                log.error("用户创建订单,套餐重复,mealId={},orderId", mealId,order.getOrderId());
                return ResultVOUtil.error(-2, "您已购买过该月套餐");
            }
        }
        List<MealInfo> mealInfoList=mealInfoService.findUpAll();
        List<String> mealIdList=new ArrayList<>();
        mealInfoList.forEach(e-> mealIdList.add(e.getMealId()));
        List<OrderMeal> orderList=mealRepository.findByBuyerOpenidAndMealIdIn(openid,mealIdList);

        if(orderList.size()>=3){
            log.error("用户创建订单,预定的套餐不能超过3个");
            return ResultVOUtil.error(-3, "最多只能预缴3个月");
        }
        BeanUtils.copyProperties(mealForm,orderMeal);
        MealInfo mealInfo=mealInfoService.findById(mealForm.getMealId());
        orderMeal.setDays(mealInfo.getDays());
        orderMeal.setSnapIcon(mealInfo.getMealIcon());
        orderMeal.setSnapName(mealInfo.getMealName());
        orderMeal.setOrderAmount(mealInfo.getMealPrice());
        orderMeal.setTotalAccount(1);
        orderMeal.setOrderId(KeyUtil.genUniqueKey());
        OrderMeal createResult=mealService.create(orderMeal);

        Map<String,String> map=new HashMap<>();
        map.put("orderId",createResult.getOrderId());

        return ResultVOUtil.success(map);
    }
    //订单列表
    @GetMapping("/list")
    public ResultVO<List<MealDTO>> list(@RequestParam("token") String token,
                                        @RequestParam(value = "page",defaultValue = "0") Integer page,
                                        @RequestParam(value = "size",defaultValue = "5") Integer size){
        String openid=buyerService.getOpenid(token);
        if(openid.equals("e")){
            return ResultVOUtil.error(-1,"relogin");
        }
        Pageable pageable=PageRequest.of(page,size, SortUtil.basicSort("desc", "createTime"));
        Page<MealDTO> MealDTOPage=mealService.findList(openid,pageable);
        return ResultVOUtil.success(MealDTOPage.getContent());
    }

    @GetMapping("/dateList")
    public ResultVO<List<MealDTO>> list(@RequestParam("token") String token,
                                        @RequestParam("start") String start,
                                        @RequestParam("end") String end,
                                        @RequestParam(value = "page",defaultValue = "0") Integer page,
                                        @RequestParam(value = "size",defaultValue = "5") Integer size){
        String openid=buyerService.getOpenid(token);
        if(openid.equals("e")){
            return ResultVOUtil.error(-1,"relogin");
        }
        Pageable pageable=PageRequest.of(page,size, SortUtil.basicSort("desc", "createTime"));
        Page<MealDTO> MealDTOPage=mealService.findList(openid,pageable,start,end);
        return ResultVOUtil.success(MealDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<MealDTO> detail (@RequestParam("token") String token,
                                      @RequestParam("orderId") String orderId){
        String openid=buyerService.getOpenid(token);
        if(openid.equals("e")){
            return ResultVOUtil.error(-1,"relogin");
        }
        MealDTO mealDTO=mealService.findById(orderId);
        return ResultVOUtil.success(mealDTO);
    }
    //申请退款
    @PostMapping("/refund")
    public ResultVO applyForRefund (@Valid RefundForm refundForm,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("申请退款，参数不正确，refundForm={}",refundForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }

        String openid=buyerService.getOpenid(refundForm.getToken());
        if(openid.equals("e")){
            return ResultVOUtil.error(-1,"relogin");
        }
        MealDTO mealDTO=mealService.findById(refundForm.getOrderId());
        if(mealDTO.getOrderStatus()==3){
            log.error("用户申请退款,有退款还未处理,orderId={},openid={}", mealDTO.getOrderId(), openid);
            return ResultVOUtil.error(-5, "有退款还未处理");
        }
        if(mealDTO.getPayStatus()==0){
            log.error("用户申请退款,支付状态不正确,orderId={},openid={}", mealDTO.getOrderId(), openid);
            return ResultVOUtil.error(-5, "支付状态不正确");
        }
        if(!mealDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("用户申请退款,订单和用户不匹配,orderId={},openid={}", mealDTO.getOrderId(), openid);
            return ResultVOUtil.error(-2, "don't match");
        }
        List<Refund> refundList=refundRepository.findByOrderId(refundForm.getOrderId());
        List<String> dateList=new ArrayList<>();
        MealInfo mealInfo=mealInfoService.findById(mealDTO.getMealId());
        for(Refund refund:refundList){
            String date= refund.getDate();
            dateList.addAll(List2StringUtil.getList(date));
        }
        String newDate=refundForm.getDate();
        String[] newDateArr=newDate.split(",");
        List<String> newDateList= DateUtil.getBetweenDates(newDateArr[0],newDateArr[1]);
        if(dateList.size()+newDateList.size()>mealInfo.getDays()){
            log.error("用户申请退款,天数超过限制,days={},limit={}", dateList.size()+newDateList.size(),mealInfo.getDays());
            return ResultVOUtil.error(-3, "over the limit");
        }
        for(String date:newDateList) {
                if (dateList.contains(date)) {
                    log.error("用户申请退款,日期重复,date={},refundId={}", date);
                    return ResultVOUtil.error(-4, date);
                }

        }
        String newDateStr=List2StringUtil.getStr(newDateList);
        Refund newRefund=new Refund();
        newRefund.setRefundId(KeyUtil.genUniqueKey());
        newRefund.setReason(refundForm.getReason());
        newRefund.setDate(refundForm.getDate());
        newRefund.setOrderId(refundForm.getOrderId());
        newRefund.setDate(newDateStr);
        newRefund.setDays(newDateList.size());

        BigDecimal mealPrice=mealInfo.getMealPrice();
        BigDecimal refundPrice=mealPrice.divide(new BigDecimal(mealInfo.getDays()),0).multiply(new BigDecimal(newRefund.getDays()));
        newRefund.setPrice(refundPrice);
        MealDTO result = mealService.applyForRefund(mealDTO, newRefund);
        if (result==null) return ResultVOUtil.error(-5,"error");
        Map<String,Refund> map=new HashMap<>();
        map.put("refund",newRefund);
        return ResultVOUtil.success(map);
    }

    //取消未支付订单
    //用户暂不可自动退款
    @PostMapping("/cancel")
    public ResultVO userCancel (@RequestParam("token") String token,
                                @RequestParam("orderId") String orderId){
        String openid=buyerService.getOpenid(token);
        if(openid.equals("e")){
            return ResultVOUtil.error(-1,"relogin");
        }
        MealDTO mealDTO=mealService.findById(orderId);

        if(!mealDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("用户取消,订单和用户不匹配,orderId={},openid={}", mealDTO.getOrderId(), openid);
            return ResultVOUtil.error(-2, "don't match");
        }
        mealService.userCancel(mealDTO);
        return ResultVOUtil.success();
    }
}
