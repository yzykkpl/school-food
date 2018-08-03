package com.yzy.canteen.dataobject;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.Date;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-19 10:34
 */
@Entity
@Data
public class SimuOrder {
    @Id
    private String detailId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;
    /** 商品名称. */
    private String productName;
    /** 商品数量. */
    private Integer productQuantity;
    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;
}
