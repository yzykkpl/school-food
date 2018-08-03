package com.yzy.canteen.service;

import com.yzy.canteen.utils.payUtil.model.RefundResponse;
import com.yzy.canteen.dto.OrderDTO;

/**
 * @description:
 * @author: yzy
 * @create: 2018-04-04 20:20
 */
public interface PayService {
    com.yzy.canteen.utils.payUtil.model.PayResponse create(OrderDTO orderDTO);

    com.yzy.canteen.utils.payUtil.model.PayResponse notify(String notifyData);
    //全额退款
    RefundResponse refund(OrderDTO orderDTO);
}
