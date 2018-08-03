package com.yzy.canteen.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yzy.canteen.enums.ProductStatusEnum;
import com.yzy.canteen.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 商品
 * @author: yzy
 * @create: 2018-03-24 22:45
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private String  productDescription;

    private String productIcon;

    private String detailImage;
    //0正常  1下架
    private Integer productStatus=ProductStatusEnum.UP.getCode();

    private Integer categoryType;

    private Integer schoolId=-1;

    private String schoolName="全部";

    private Date createTime;

    private Date updateTime;
    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }

}
