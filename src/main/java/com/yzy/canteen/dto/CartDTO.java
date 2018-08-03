package com.yzy.canteen.dto;

import lombok.Data;

/**
 * @description: 购物车
 * @author: yzy
 * @create: 2018-03-25 20:41
 */
@Data
public class CartDTO {
    //商品ID
    private  String productId;
    //数量
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
