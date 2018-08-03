package com.yzy.canteen.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yzy.canteen.dataobject.ProductInfo;
import com.yzy.canteen.enums.OrderStatusEnum;
import com.yzy.canteen.enums.PayStatusEnum;
import com.yzy.canteen.utils.EnumUtil;
import com.yzy.canteen.utils.serializer.Date2LongSerializer;
import com.yzy.canteen.utils.serializer.Date2StringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)//不显示为NULL的字段，已在yml配置文件中统一配置
public class OrderDTO {

    /** 订单id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 学校. */
    private String buyerSchool;
    /** 班级. */
    private String buyerCls;

    private String stdNum;

    /** 微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;


    /** 第一件商品名. */
    private String snapName;

    /** 第一件商图标. */
    private String snapIcon;

    private String productId;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;
//    @JsonSerialize(using = Date2StringSerializer.class)
//    private Date startDate;
//    @JsonSerialize(using = Date2StringSerializer.class)
//    private Date endDate;
    @JsonSerialize(using = Date2StringSerializer.class)
    private Date date;

    //    预定的数量
    private Integer counts;

    //备注
    private String comment;

    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)//将data转为long再添加为Json数据
    private Date updateTime;

    private ProductInfo productInfo;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
