package com.yzy.canteen.controller;

import com.yzy.canteen.dataobject.ClsInfo;
import com.yzy.canteen.dataobject.SchoolInfo;
import com.yzy.canteen.repository.SchoolInfoRepository;
import com.yzy.canteen.service.ClsService;
import com.yzy.canteen.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
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
 * @description: 学校管理
 * @author: yzy
 * @create: 2018-03-31 21:44
 */
@Controller
@RequestMapping("/seller/school")
@Slf4j
public class SellerSchoolController {
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private ClsService clsService;

    @Autowired
    private SchoolInfoRepository schoolInfoRepository;
    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<SchoolInfo> schoolInfoList=schoolService.findAll();
        map.put("schoolInfoList",schoolInfoList);
        return new ModelAndView("school/list",map);
    }


    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "schoolId",required = false) Integer schoolId,Map<String,Object> map){
        if(schoolId!=null){
            SchoolInfo schoolInfo=schoolService.findById(schoolId);
            map.put("schoolInfo",schoolInfo);
        }
        return new ModelAndView("school/index",map);
    }

    /**
     * 学校新增/更新
     * @param:
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid SchoolInfo schoolInfo, BindingResult bindingResult,Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/canteen/seller/school/list");
            return new ModelAndView("common/error",map);
        }
        try {
            if(schoolInfo.getSchoolId()!=null) {
                String schoolName=schoolInfo.getSchoolName();
                schoolInfo = schoolService.findById(schoolInfo.getSchoolId());
                schoolInfo.setSchoolName(schoolName);
            }
            schoolInfo=schoolService.save(schoolInfo);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/canteen/seller/school/index?schoolId="+schoolInfo.getSchoolId());
            return new ModelAndView("common/error",map);
        }
        map.put("url","/canteen/seller/school/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("schoolId") Integer schoolId,Map<String,Object> map){
        if(schoolId==null){
            map.put("msg","学校id为空");
            map.put("url","/canteen/seller/school/list");
            return new ModelAndView("common/error",map);
        }
        SchoolInfo schoolInfo=schoolService.findById(schoolId);
        if(schoolInfo==null){
            map.put("msg","学校不存在");
            map.put("url","/canteen/seller/school/list");
            return new ModelAndView("common/error",map);
        }
        List<ClsInfo> clsInfoList = clsService.findAllBySchoolId(schoolId);
        map.put("schoolInfo",schoolInfo);
        map.put("clsInfoList",clsInfoList);
        return new ModelAndView("school/detail",map);
    }

    @GetMapping("/clsIndex")
    public ModelAndView clsIndex(@RequestParam(value = "clsId",required = false) Integer clsId,@RequestParam("schoolId") Integer schoolId,Map<String,Object> map){
        if(clsId!=null) {
            ClsInfo clsInfo=clsService.findById(clsId);
            map.put("clsInfo", clsInfo);
        }
        map.put("schoolId",schoolId);
        return new ModelAndView("school/clsIndex",map);
    }

    @PostMapping("/clsSave")
    public ModelAndView save(@Valid ClsInfo clsInfo, BindingResult bindingResult,Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/canteen/seller/school/detail?schoolId="+clsInfo.getSchoolId());
            return new ModelAndView("common/error",map);
        }
        try {
            if(clsInfo.getClsId()!=null) {
                String clsName=clsInfo.getClsName();
                clsInfo = clsService.findById(clsInfo.getClsId());
                clsInfo.setClsName(clsName);
            }
            clsInfo=clsService.save(clsInfo);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/canteen/seller/school/clsIndex?clsId="+clsInfo.getClsId());
            return new ModelAndView("common/error",map);
        }
        map.put("url","/canteen/seller/school/detail?schoolId="+clsInfo.getSchoolId());
        return new ModelAndView("common/success",map);
    }



}
