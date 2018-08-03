package com.yzy.canteen.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yzy.canteen.dataobject.MealInfo;
import com.yzy.canteen.enums.OrderStatusEnum;
import com.yzy.canteen.enums.PayStatusEnum;
import com.yzy.canteen.utils.serializer.Date2LongSerializer;
import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-24 12:52
 */
@Data
public class MealDTO {
    private String orderId;

    /** 名字. */
    private String buyerName;

    /** 手机号. */
    private String buyerPhone;

    /** 学校. */
    private String buyerSchool;
    /** 班级. */
    private String buyerCls;

    /** 学号. */
    private String stdNum;
    /** 微信Openid. */
    private String buyerOpenid;

    /** 套餐id. */
    private String mealId;

    private MealInfo mealInfo;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 商品总数. */
    private Integer totalAccount;

    /** 第一件商品名. */
    private String snapName;

    /** 第一件商图标. */
    private String snapIcon;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /*当月天数*/
    private Integer days;

    //备注
    private String comment;

    /*创建时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)//将data转为long再添加为Json数据
    private Date updateTime;

}
