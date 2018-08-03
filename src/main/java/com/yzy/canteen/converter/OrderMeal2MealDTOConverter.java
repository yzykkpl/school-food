package com.yzy.canteen.converter;

import com.yzy.canteen.dataobject.OrderMeal;
import com.yzy.canteen.dto.MealDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-24 12:59
 */
public class OrderMeal2MealDTOConverter {
    public static MealDTO convert(OrderMeal orderMeal){
        MealDTO mealDTO = new MealDTO();
        BeanUtils.copyProperties(orderMeal,mealDTO);
        return mealDTO;
    }
    public static List<MealDTO> convert(List<OrderMeal> orderMealList){
        return orderMealList.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
