package com.yzy.canteen.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.constant.CookieConstant;
import com.yzy.canteen.constant.TokenConstant;
import com.yzy.canteen.dataobject.SellerInfo;
import com.yzy.canteen.dataobject.UserInfo;
import com.yzy.canteen.dto.MealDTO;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.repository.UserInfoRepository;
import com.yzy.canteen.service.SchoolService;
import com.yzy.canteen.service.SellerService;
import com.yzy.canteen.service.UserService;
import com.yzy.canteen.utils.CookieUtil;
import com.yzy.canteen.utils.RSAUtil;
import com.yzy.canteen.utils.ResultVOUtil;
import com.yzy.canteen.utils.SortUtil;
import com.yzy.canteen.viewobject.PageVO;
import com.yzy.canteen.viewobject.ResultVO;
import com.yzy.canteen.viewobject.SchoolVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: 卖家用户
 * @author: yzy
 * @create: 2018-04-01 18:45
 */
@Slf4j
@Controller
@RequestMapping("/seller")
public class SellerUserController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private Gson gson;


    @RequestMapping("/login")
    @ResponseBody
    public ResultVO<Map<String, String>> login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse resp) {

        String pwd = RSAUtil.decryptBase64(password);


        SellerInfo sellerInfo = sellerService.findSellerInfoByUsername(username);
        if (sellerInfo == null || !sellerInfo.getPassword().equals(pwd)) {
            return ResultVOUtil.error(-1, "用户名或密码错误");
        }

        //设置TOKEN至Redis
        String token = UUID.randomUUID().toString();
        Integer expire = TokenConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(TokenConstant.TOKEN_PREFIX, token), username, expire, TimeUnit.SECONDS);

        //设置TOKEN至cookie (不保存cookie)
        CookieUtil.set(resp, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);

        return ResultVOUtil.success();
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse resp, HttpServletRequest req, Map<String, Object> map) {
        //从Cookie中查询
        Cookie cookie = CookieUtil.get(req, CookieConstant.TOKEN);
        if (cookie != null) {
            //清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(TokenConstant.TOKEN_PREFIX, cookie.getValue()));
            //清除Cookie
            CookieUtil.set(resp, CookieConstant.TOKEN, null, 0);
        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/canteen/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/user/list")
    public ModelAndView list(HttpServletRequest req, Map<String, Object> map) {
        //从Cookie中查询
        Cookie cookie = CookieUtil.get(req, CookieConstant.TOKEN);
        if (cookie != null) {
            String username=redisTemplate.opsForValue().get(String.format(TokenConstant.TOKEN_PREFIX, cookie.getValue()));
            map.put("msg", "您不是管理员");
            map.put("url", "/canteen/seller/order/list");
            if(!username.equals("admin")) return new ModelAndView("common/error",map);
            else {
                List<SellerInfo> sellerInfoList=sellerService.findAll();
                map.put("sellerInfoList",sellerInfoList);
                return new ModelAndView("seller/list",map);
            }

        }
        else
            return new ModelAndView("redirect:/canteen/seller/order/list");

    }
    @ResponseBody
    @RequestMapping("/user/change")
    public ResultVO change(@RequestParam(value = "username") String username,@RequestParam("password") String password){
        SellerInfo sellerInfo=sellerService.update(username,password);
        if(sellerInfo==null) return ResultVOUtil.error(-1,"error");
        return ResultVOUtil.success();
    }
    @ResponseBody
    @RequestMapping("/user/create")
    public ResultVO create(@RequestParam( "username") String username,@RequestParam("password") String password){
        SellerInfo sellerInfo=sellerService.save(username,password);
        if(sellerInfo==null) return ResultVOUtil.error(-1,"用户已存在");
        return ResultVOUtil.success();
    }
    @ResponseBody
    @RequestMapping("/user/delete")
    public ResultVO delete(@RequestParam("username") String username){
        sellerService.delete(username);
        return ResultVOUtil.success();
    }

    @GetMapping("std/list")
    public ModelAndView list(Map<String,Object> map) {
        List<SchoolVO> schoolVOList = schoolService.findAllWithCls();
        Gson gson = new Gson();
        String schoolListJson = gson.toJson(schoolVOList);
        map.put("schoolList", schoolVOList);
        map.put("schoolListJson", schoolListJson);
        ModelAndView model = new ModelAndView("user/list", map);
        return model;


    }
    @ResponseBody
    @PostMapping("/std/select")
    public PageVO stdList(@RequestParam(value = "schoolId",required = false) Integer schoolId ,
                          @RequestParam(value = "name",required = false) String name,
                          @RequestParam(value = "cls",required = false) String cls,
                          @RequestParam(value = "status",defaultValue ="-2") Integer status,
                          @RequestParam(value = "pageNumber",defaultValue = "1") Integer page,
                          @RequestParam(value = "pageSize",defaultValue = "20") Integer size
                          ) {
        name=name.isEmpty()?null:name;
        cls=cls.isEmpty()?null:cls;
        List<Integer> statusList=new ArrayList<>();
        if(status==-2) {

            statusList.add(0);
            statusList.add(1);
        }
        else statusList.add(status);
        Pageable pageable= PageRequest.of(page-1,size, SortUtil.basicSort("desc", "schoolId"));
        Page<UserInfo> userInfoPage =null;

        if(schoolId!=null&&name==null&&cls==null){
            userInfoPage=userInfoRepository.findBySchoolIdAndStatusIn(schoolId,statusList,pageable);
        }
        else if(schoolId!=null&&name==null&&cls!=null){
            userInfoPage=userInfoRepository.findBySchoolIdAndClsAndStatus(schoolId,cls,0,pageable);
        }
        else if(schoolId!=null&&name!=null&&cls!=null){
            userInfoPage=userInfoRepository.findBySchoolIdAndNameAndClsAndStatus(schoolId,name,cls,0,pageable);
        }
        else if(schoolId!=null&&name!=null&&cls==null){
            userInfoPage=userInfoRepository.findBySchoolIdAndNameAndStatusIn(schoolId,name,statusList,pageable);
        }
        else if(schoolId==null&&name!=null){
            userInfoPage=userInfoRepository.findByNameAndStatusIn(name,statusList,pageable);
        }
        else
            userInfoPage =userInfoRepository.findAllByStatusIsNot(-1,pageable);
        PageVO pageVO = new PageVO();
        List<UserInfo> userInfoList=userInfoPage.getContent();
        pageVO.setPageNumber(page);
        pageVO.setPageSize(userInfoPage.getSize());
        pageVO.setTotalPage(userInfoPage.getTotalPages());
        pageVO.setTotalRow(userInfoPage.getTotalElements());
        pageVO.setList(userInfoList);
        return pageVO;
    }

    @ResponseBody
    @RequestMapping("/std/delete")
    public ResultVO stdDelete(@RequestParam(value = "ids[]") Integer[] ids){


        List<Integer> idList=Arrays.asList(ids);
        log.info(idList.toString());
//        try {
//            idList = gson.fromJson(arr, new TypeToken<List<Integer>>() {
//            }.getType());
//        } catch (Exception e) {
//            log.error("对象转换失败,string={}", arr);
//            throw new SellException(ResultEnum.PARAM_ERROR);
//        }
        List<UserInfo> result=userService.delete(idList);
        if(result==null)
            return ResultVOUtil.error(-1,"删除失败");
        else
            return ResultVOUtil.success();

    }


}

