package com.yzy.canteen.service;

import com.yzy.canteen.dataobject.MealInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-23 21:55
 */
public interface MealInfoService {
    MealInfo findById(String mealId);

    /**
     * 查询在架套餐列表
     */
    List<MealInfo> findUpAll();

    Page<MealInfo> findAll(Pageable pageable);

    MealInfo save(MealInfo mealInfo);

    //上架
    MealInfo onSale(String mealId);

    //下架
    MealInfo offSale(String mealId);
}
