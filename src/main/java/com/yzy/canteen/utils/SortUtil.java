package com.yzy.canteen.utils;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 排序封装
 * @author: yzy
 * @create: 2018-04-07 16:17
 */
public class SortUtil {

    public static Sort basicSort() {
        return basicSort("desc", "id");
    }

    public static Sort basicSort(String orderType, String orderField) {
        Sort sort = new Sort(Sort.Direction.fromString(orderType), orderField);
        return sort;
    }
    public static Sort twoSort(String orderType1, String orderField1,String orderType2,String orderField2) {
        List<Sort.Order> orderList=new ArrayList<>();
        Sort.Order order1=new Sort.Order(Sort.Direction.fromString(orderType1),orderField1);
        Sort.Order order2=new Sort.Order(Sort.Direction.fromString(orderType2),orderField2);
        orderList.add(order1);
        orderList.add(order2);
        Sort sort = Sort.by(orderList);
        return sort;
    }
}
