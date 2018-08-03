package com.yzy.canteen.form;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * @description: 商品表单
 * @author: yzy
 * @create: 2018-04-01 13:45
 */
@Data
public class ProductForm {

    private String productId;

    @NotEmpty(message = "名称必填")
    private String productName;

    @NotNull(message = "单价必填")
    private BigDecimal productPrice;

    private String detailImage;

    private String  productDescription;
    @NotEmpty(message = "图片地址必填")
    private String productIcon;

    private Integer categoryType;

    private Integer schoolId=-1;


}
