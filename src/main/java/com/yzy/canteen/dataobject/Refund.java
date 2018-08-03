package com.yzy.canteen.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-23 20:59
 */
@Entity
@Data
@DynamicUpdate
public class Refund {
    @Id
    private String refundId;

    private String orderId;

    private String reason;

    private String date;

    private Integer days;

    private BigDecimal price;
    //退款状态 0-待处理  1-已退款
    private Integer status=0;
}
