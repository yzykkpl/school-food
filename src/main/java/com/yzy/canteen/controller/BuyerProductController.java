package com.yzy.canteen.controller;

import com.yzy.canteen.dataobject.MealInfo;
import com.yzy.canteen.dataobject.ProductCategory;
import com.yzy.canteen.dataobject.ProductInfo;
import com.yzy.canteen.repository.MealInfoRepository;
import com.yzy.canteen.repository.ProductInfoRepository;
import com.yzy.canteen.service.CategoryService;
import com.yzy.canteen.service.MealInfoService;
import com.yzy.canteen.service.ProductService;
import com.yzy.canteen.utils.ResultVOUtil;
import com.yzy.canteen.utils.SortUtil;
import com.yzy.canteen.viewobject.MealInfoVO;
import com.yzy.canteen.viewobject.ProductInfoVO;
import com.yzy.canteen.viewobject.ProductVO;
import com.yzy.canteen.viewobject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 买家商品controller
 * @author: yzy
 * @create: 2018-03-25 11:03
 */

@RestController
@RequestMapping("/buyer/product")
@Slf4j
public class BuyerProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MealInfoService mealInfoService;

    @Autowired
    private MealInfoRepository mealInfoRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    //查询所有商品（按类目返回）
    @GetMapping("/list")
    public ResultVO list(@RequestParam("schoolId") Integer schoolId) {
        //1 查询所有上架商品(降序)

        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatusAndSchoolId(0,schoolId,SortUtil.basicSort("desc", "createTime"));
        List<MealInfo> mealInfoList=mealInfoRepository.findByMealStatusAndSchoolId(0,schoolId);
        List<ProductInfoVO> productInfoVOListAll=new ArrayList<>();
        List<ProductVO> productVOList = new ArrayList<>();
        //先将所有商品放入一个list
        productInfoList.forEach(productInfo -> {
            ProductInfoVO productInfoVO = new ProductInfoVO();
            BeanUtils.copyProperties(productInfo, productInfoVO);
            productInfoVOListAll.add(productInfoVO);
        });
        List<ProductInfoVO> mealInfoVOList=new ArrayList<>();
        mealInfoList.forEach(mealInfo -> {
            MealInfoVO mealInfoVO = new MealInfoVO();
            BeanUtils.copyProperties(mealInfo, mealInfoVO);
            mealInfoVOList.add(mealInfoVO);
            productInfoVOListAll.add(mealInfoVO);
        });
        //所有商品单独放在一个productVO里
        ProductVO allProductVO=new ProductVO();
        allProductVO.setCategoryType(0);
        allProductVO.setCategoryName("所有商品");
        allProductVO.setProductInfoVOList(productInfoVOListAll);
        productVOList.add(allProductVO);
        //将套餐放入第一个类目
        ProductVO allMealVO=new ProductVO();
        allMealVO.setCategoryType(1);
        allMealVO.setCategoryName("套餐");
        //套餐的类目图片为商品类目1的图片
        ProductCategory category = categoryService.findById(1);
        allMealVO.setCategoryIcon(category.getCategoryIcon());
        allMealVO.setProductInfoVOList(mealInfoVOList);
        productVOList.add(allMealVO);
        //2 查询类目（一次性查询）
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3 数据拼装

        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryIcon(productCategory.getCategoryIcon());
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();

            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }


        return ResultVOUtil.success(productVOList);
    }


}

