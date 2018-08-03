package com.yzy.canteen.repository;

import com.yzy.canteen.dataobject.OrderMeal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-23 18:50
 */
public interface OrderMealRepository extends JpaRepository<OrderMeal,String>{
    Page<OrderMeal> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
    Page<OrderMeal> findByOrderStatus(Integer orderStatus, Pageable pageable);
    List<OrderMeal> findByBuyerOpenidAndPayStatus(String openid,Integer payStatus);
    Page<OrderMeal> findAllByPayStatus(Integer payStatus,Pageable pageable);
    OrderMeal findByOrderId(String orderId);
    List<OrderMeal> findByBuyerOpenidAndPayStatusAndOrderStatusNot(String openid,Integer payStatus,Integer orderStatus);
    Page<OrderMeal> findByBuyerOpenidAndCreateTimeBetween(String buyerOpenid, Pageable pageable, Date start, Date end);
    List<OrderMeal> findByBuyerOpenidAndMealIdIn(String openid,List<String> mealIdList);
    List<OrderMeal> findByBuyerOpenid(String buyerOpenid);

    //查找对应支付状态的所有学号
    @Query(" select o.stdNum from OrderMeal o where o.payStatus=:status and o.mealId=:mealId")
    List<String> findAllStdNumByPayStatusAndMealId(@Param("status") Integer status,@Param("mealId") String mealId);


    //按学校查询
    Page<OrderMeal> findByBuyerSchoolAndPayStatus(String school,Integer payStatus,Pageable pageable);

    //按学校+班级查询
    Page<OrderMeal> findByBuyerSchoolAndBuyerClsAndPayStatus(@Param("school") String school,@Param("cls") String cls,@Param("status") Integer payStatus,Pageable pageable);

    //按学校+班级+套餐编号
    Page<OrderMeal> findByBuyerSchoolAndBuyerClsAndMealIdAndPayStatus(String school,String cls,String mealId,Integer payStatus,Pageable pageable);

    //按学校+套餐编号
    Page<OrderMeal> findByBuyerSchoolAndMealIdAndPayStatus(String school,String mealId,Integer payStatus,Pageable pageable);
    //按时间段查询
    Page<OrderMeal> findByCreateTimeBetweenAndPayStatus(Date start, Date end,Integer payStatus,Pageable pageable);

    //按时学校+时间段
    Page<OrderMeal> findByBuyerSchoolAndCreateTimeBetweenAndPayStatus(String school,Date start, Date end,Integer payStatus,Pageable pageable);

    //按学校+班级+时间段
    Page<OrderMeal> findByBuyerSchoolAndBuyerClsAndCreateTimeBetweenAndPayStatus(String school,String cls,Date start, Date end,Integer payStatus,Pageable pageable);


    List<OrderMeal> findByBuyerSchoolAndMealIdAndPayStatus(String school,String mealId,Integer paystatus,Sort sort);
}
