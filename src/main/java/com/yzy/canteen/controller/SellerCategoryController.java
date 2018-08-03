package com.yzy.canteen.controller;

import com.yzy.canteen.dataobject.ProductCategory;
import com.yzy.canteen.form.CategoryForm;
import com.yzy.canteen.service.CategoryService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @description: 卖家类目管理
 * @author: yzy
 * @create: 2018-04-01 14:44
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> categoryList=categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("category/list",map);
    }
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,Map<String,Object> map){
        ProductCategory category=new ProductCategory();
        if (categoryId!=null){
            category=categoryService.findById(categoryId);
            map.put("category",category);
        }
        return new ModelAndView("category/index",map);
    }

    /**
     * 类目新增/更新
     * @param:
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult bindingResult, Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/canteen/seller/category/index?"+categoryForm.getCategoryId());
            return new ModelAndView("common/error",map);
        }
        ProductCategory category=new ProductCategory();
        try {
            if(categoryForm.getCategoryId()!=null){
                category = categoryService.findById(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm,category);
            categoryService.save(category);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/canteen/seller/product/index?"+categoryForm.getCategoryId());
            return new ModelAndView("common/error",map);
        }
        map.put("url","/canteen/seller/category/list");
        return new ModelAndView("common/success",map);
    }
}
