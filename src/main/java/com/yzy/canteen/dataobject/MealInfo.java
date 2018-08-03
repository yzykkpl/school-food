package com.yzy.canteen.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yzy.canteen.enums.ProductStatusEnum;
import com.yzy.canteen.utils.EnumUtil;
import com.yzy.canteen.utils.serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-23 21:56
 */
@Entity
@Data
public class MealInfo {
    @Id
    private String mealId;

    private String mealName;

    private BigDecimal mealPrice;

    private String  mealDescription;

    private String mealIcon;

    private String detailImage;
    //0正常  1下架
    private Integer mealStatus= ProductStatusEnum.UP.getCode();

    private Integer days;

    private Integer schoolId=-1;

    private String schoolName="全部";

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date mealDate;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    @JsonIgnore
    public ProductStatusEnum getMealStatusEnum(){
        return EnumUtil.getByCode(mealStatus, ProductStatusEnum.class);
    }
}
