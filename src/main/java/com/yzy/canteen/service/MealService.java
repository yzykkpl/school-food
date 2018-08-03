package com.yzy.canteen.service;

import com.yzy.canteen.dataobject.OrderMeal;
import com.yzy.canteen.dataobject.Refund;
import com.yzy.canteen.dto.MealDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description: 套餐订单
 * @author: yzy
 * @create: 2018-05-23 19:07
 */
public interface MealService {
    /** 创建订单. */
    OrderMeal create(OrderMeal orderMeal);

    /** 查询单个订单. */
    MealDTO findById(String orderId);

    /** 查询所有订单列表. */
    Page<MealDTO> findList(String buyerOpenid, Pageable pageable);

    /** 查询将来月份的订单列表. */
    List<MealDTO> findFutureList(String buyerOpenid);

    /** 卖家取消订单. */
    OrderMeal cancel(OrderMeal orderMeal);

    /** 买家取消订单. */
    MealDTO userCancel(MealDTO mealDTO);

    /** 完结订单. */
    OrderMeal finish(OrderMeal orderMeal);

    /** 支付订单. */
    MealDTO paid(MealDTO mealDTO);

    /** (后台)查询所有用户订单列表. */
    Page<MealDTO> findList(Pageable pageable);

    /** 买家申请退款 */
    MealDTO applyForRefund(MealDTO mealDTO, Refund refund);
    /** 退款(后台) */
    MealDTO refund(MealDTO mealDTO,String refundId);

    /** 查找订单列表-根据订单状态 */
    Page<MealDTO> findListByOrderStatus(Integer orderStatus,Pageable pageable);

    /** 查找订单列表-根据openid和时间段 */
    Page<MealDTO> findList(String buyerOpenid, Pageable pageable,String start,String end);

    /** (后台)查找订单列表-根据时间段 */
    Page<MealDTO> findListWithDate(Pageable pageable,String start,String end);
    /** (后台)查找订单列表-根据学校 */
    Page<MealDTO> findListWithSchool(Pageable pageable,String school);
    /** (后台)查找订单列表-根据学校+时间段 */
    Page<MealDTO> findListWithSchoolAndDate(Pageable pageable,String school,String start,String end);
    /** (后台)查找订单列表-根据学校+班级 */
    Page<MealDTO> findListWithSchoolAndCls(Pageable pageable,String school,String cls);
    /** (后台)查找订单列表-根据学校+班级+时间段 */
    Page<MealDTO> findListWithSchoolAndClsAndDate(Pageable pageable,String school,String cls,String start,String end);
    /** (后台)查找订单列表-根据学校+套餐id */
    Page<MealDTO> findListWithSchoolAndMealId(Pageable pageable,String school,String mealId);
    /** (后台)查找订单列表-根据学校+班级+套餐id */
    Page<MealDTO> findListWithSchoolAndClsAndMealId(Pageable pageable,String school,String cls,String mealId);





}
