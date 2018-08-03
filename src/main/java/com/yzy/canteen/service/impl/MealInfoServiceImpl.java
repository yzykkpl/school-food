package com.yzy.canteen.service.impl;

import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.dataobject.MealInfo;
import com.yzy.canteen.enums.ProductStatusEnum;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.repository.MealInfoRepository;
import com.yzy.canteen.service.MealInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-23 22:07
 */
@Service
public class MealInfoServiceImpl implements MealInfoService {
    @Autowired
    MealInfoRepository repository;
    @Override
    public MealInfo findById(String mealId) {
        return repository.findById(mealId).get();
    }

    @Override
    public List<MealInfo> findUpAll() {
        return repository.findByMealStatus(0);
    }

    @Override
    public Page<MealInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public MealInfo save(MealInfo mealInfo) {
        return repository.save(mealInfo);
    }

    @Override
    public MealInfo onSale(String mealId) {
        MealInfo mealInfo=repository.findById(mealId).get();
        if(mealInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(mealInfo.getMealStatusEnum()== ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        mealInfo.setMealStatus(ProductStatusEnum.UP.getCode());

        return repository.save(mealInfo);
    }

    @Override
    public MealInfo offSale(String mealId) {
        MealInfo mealInfo=repository.findById(mealId).get();
        if(mealInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(mealInfo.getMealStatusEnum()== ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        mealInfo.setMealStatus(ProductStatusEnum.DOWN.getCode());

        return repository.save(mealInfo);
    }
}
