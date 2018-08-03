package com.yzy.canteen.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @description: 商品类目
 * @author: yzy
 * @create: 2018-03-25 11:44
 */
@Data
public class ProductVO {
    @JsonProperty("name") //转为JSON后的字段名
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("icon")
    private String categoryIcon;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
