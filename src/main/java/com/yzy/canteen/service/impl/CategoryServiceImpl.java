package com.yzy.canteen.service.impl;

import com.yzy.canteen.dataobject.ProductCategory;
import com.yzy.canteen.repository.ProductCategoryRepository;
import com.yzy.canteen.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: CategoryServiceImpl
 * @author: yzy
 * @create: 2018-03-24 22:29
 */

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findById(Integer categoryId) {
        return repository.findById(categoryId).get();
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
