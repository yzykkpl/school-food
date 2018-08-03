package com.yzy.canteen.service;

import com.yzy.canteen.dataobject.Refund;
import com.yzy.canteen.dto.MealDTO;
import com.yzy.canteen.utils.payUtil.model.RefundResponse;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-25 15:27
 */
public interface MealPayService {
    com.yzy.canteen.utils.payUtil.model.PayResponse create(MealDTO mealDTO);

    com.yzy.canteen.utils.payUtil.model.PayResponse notify(String notifyData);
    //全额退款
    RefundResponse refund(MealDTO mealDTO);
    //部分退款
    RefundResponse rebate(MealDTO mealDTO,Refund refund);
}
