package com.yzy.canteen.dataobject;

/**
 * @description: 订单详情
 * @author: yzy
 * @create: 2018-03-25 19:40
 */
import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
public class OrderDetail {

    @Id
    private String detailId;

    /** 订单id. */
    private String orderId;

    /** 商品id. */
    private String productId;

    /** 商品名称. */
    private String productName;

    /** 商品单价. */
    private BigDecimal productPrice;

    /** 商品数量. */
    private Integer productQuantity;

    /** 起始日期. */
    private Date startDate;
    /** 结束日期. */
    private Date endDate;

    /** 商品小图. */
    private String productIcon;
}
