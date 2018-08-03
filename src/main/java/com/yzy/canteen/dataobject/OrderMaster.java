package com.yzy.canteen.dataobject;

/**
 * @description: 订单
 * @author: yzy
 * @create: 2018-03-25 17:05
 */
import com.yzy.canteen.enums.OrderStatusEnum;
import com.yzy.canteen.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /** 订单id. */
    @Id
    private String orderId;

    /** 名字. */
    private String buyerName;

    /** 手机号. */
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
    /** 商品id */
    private String productId;

    private Date date;

//    private Date startDate;
//
//    private Date endDate;

    //    预定的数量
    private Integer counts;
    //备注
    private String comment;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

}
