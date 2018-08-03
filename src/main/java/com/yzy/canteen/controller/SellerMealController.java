package com.yzy.canteen.controller;

import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.dataobject.MealInfo;
import com.yzy.canteen.dataobject.SchoolInfo;
import com.yzy.canteen.form.MealInfoForm;
import com.yzy.canteen.repository.SchoolInfoRepository;
import com.yzy.canteen.service.MealInfoService;
import com.yzy.canteen.utils.DateUtil;
import com.yzy.canteen.utils.KeyUtil;
import com.yzy.canteen.utils.SortUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.NoSuchElementException;

/**
 * @description: 卖家套餐
 * @author: yzy
 * @create: 2018-05-28 12:07
 */

@Controller
@RequestMapping("/seller/meal")
public class SellerMealController {
    @Autowired
    private MealInfoService mealInfoService;
    @Autowired
    private SchoolInfoRepository schoolInfoRepository;
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value="page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "20") Integer size, Map<String,Object> map){
        Pageable pageable= PageRequest.of(page-1,size,SortUtil.basicSort("desc", "createTime"));
        Page<MealInfo> mealInfoPage=mealInfoService.findAll(pageable);
        Integer difference=size-mealInfoPage.getContent().size();
        map.put("mealInfoPage",mealInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        map.put("difference",difference);
        return new ModelAndView("meal/list",map);
    }


    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("mealId") String mealId,Map<String,Object> map){
        try{
            mealInfoService.onSale(mealId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/canteen/seller/meal/list");
            return new ModelAndView("common/error",map);
        }catch (NoSuchElementException e){
            map.put("msg",e.getMessage());
            map.put("url","/canteen/seller/meal/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/canteen/seller/meal/list");
        return new ModelAndView("common/success",map);
    }
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("mealId") String mealId,Map<String,Object> map){
        try{
            mealInfoService.offSale(mealId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/canteen/seller/meal/list");
            return new ModelAndView("common/error",map);
        }catch (NoSuchElementException e){
            map.put("msg",e.getMessage());
            map.put("url","/canteen/seller/meal/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/canteen/seller/meal/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "mealId",required = false) String mealId,Map<String,Object> map){
        if(mealId!=null&&!mealId.isEmpty()){
            MealInfo mealInfo=mealInfoService.findById(mealId);
            map.put("mealInfo",mealInfo);
        }

        //查询所有类目
        List<SchoolInfo> schoolInfoList=schoolInfoRepository.findAll();
        map.put("schoolInfoList",schoolInfoList);
        return new ModelAndView("meal/index",map);
    }

    /**
     * 商品新增/更新
     * @param:
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid MealInfoForm mealInfoForm, BindingResult bindingResult, Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/canteen/seller/meal/index?mealId="+mealInfoForm.getMealId());
            return new ModelAndView("common/error",map);
        }
        MealInfo mealInfo=new MealInfo();
        try {
            if(mealInfoForm.getMealId().isEmpty()){
                mealInfoForm.setMealId(KeyUtil.genUniqueKey());
            }
            else {
                mealInfo = mealInfoService.findById(mealInfoForm.getMealId());
            }
            BeanUtils.copyProperties(mealInfoForm,mealInfo);
            Integer schoolId=mealInfo.getSchoolId();
            if(schoolId==-1){
                mealInfo.setSchoolName("全部");
            }
            else{
                String schoolName=schoolInfoRepository.findById(schoolId).get().getSchoolName();
                mealInfo.setSchoolName(schoolName);
            }
            mealInfo.setMealDate(DateUtil.getDate(mealInfoForm.getMealDate()));
            mealInfoService.save(mealInfo);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/canteen/seller/meal/index?mealId="+mealInfoForm.getMealId());
            return new ModelAndView("common/error",map);
        }
        map.put("url","/canteen/seller/meal/list");
        return new ModelAndView("common/success",map);
    }

}
