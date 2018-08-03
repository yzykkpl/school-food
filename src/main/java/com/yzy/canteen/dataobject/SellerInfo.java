package com.yzy.canteen.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @description: 卖家信息表
 * @author: yzy
 * @create: 2018-04-01 16:04
 */
@Entity
@Data
@DynamicUpdate
public class SellerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sellerId;

    private String username;

    private String password;

}
