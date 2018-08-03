package com.yzy.canteen.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-24 15:15
 */
@Data
public class MealInfoVO extends ProductInfoVO{
    @JsonProperty("id")
    private String mealId;
    @JsonProperty("name")
    private String mealName;
    @JsonProperty("price")
    private BigDecimal mealPrice;
    @JsonProperty("description")
    private String  mealDescription;

    @JsonProperty("icon")
    private String mealIcon;

    @JsonProperty("image")
    private String detailImage;
    //0正常  1下架
    @JsonProperty("days")
    private Integer days;

    @JsonProperty("type")
    private Integer categoryType=1;

}
