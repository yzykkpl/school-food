package com.yzy.canteen.converter;

import com.yzy.canteen.dto.OrderDTO;
import com.yzy.canteen.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: yzy
 * @create: 2018-03-26 19:22
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
/*        Gson gson = new Gson();

        Integer totalAccount=0;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerSchool(orderForm.getSchool());
        orderDTO.setBuyerCls(orderForm.getCls());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());

            for(OrderDetail item:orderDetailList){
                totalAccount +=item.getProductQuantity();
            }
        } catch (Exception e) {
            log.error("对象转换失败,string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setTotalAccount(totalAccount);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;*/
        return null;
    }

}
