package com.yzy.canteen.service.impl;

import com.yzy.canteen.dataobject.UserInfo;
import com.yzy.canteen.repository.OrderMealRepository;
import com.yzy.canteen.repository.UserInfoRepository;
import com.yzy.canteen.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: yzy
 * @create: 2018-04-03 17:55
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoRepository repository;
    @Autowired
    private OrderMealRepository orderMealRepository;

    @Override
    public Page<UserInfo> findStudentNotPaid(String mealId,String school, Pageable pageable) {
        List<String> paidStdNumList=orderMealRepository.findAllStdNumByPayStatusAndMealId(1, mealId);
        if(paidStdNumList.size()==0) paidStdNumList.add("-1");
        Page<UserInfo> userInfoPage=repository.findByStatusAndSchoolAndStdNumNotIn(0,school,paidStdNumList,pageable);

        return userInfoPage;
    }

    @Override
    public Page<UserInfo> findAll(Pageable pageable) {
        return repository.findAllByStatusIsNot(-1,pageable);
    }

    @Override
    public UserInfo findUserByOpenid(String openid) {
        UserInfo userInfo=repository.findByOpenid(openid);
        if(userInfo==null)
            return null;
        return userInfo;
    }

    public UserInfo create(String openid,String name){
        UserInfo userInfo=new UserInfo();
        userInfo.setOpenid(openid);
        UserInfo result=repository.save(userInfo);
        if(result==null){
            log.error("用户创建失败，openid={},name={}",openid,name);
        }
        return result;
    }

    @Override
    public List<UserInfo> delete(List<Integer> idList) {
        List<UserInfo> userInfoList=repository.findAllById(idList);
        userInfoList.stream().forEach(e->e.setStatus(-1));
        List<UserInfo> result=repository.saveAll(userInfoList);
        if(result==null){
            log.error("用户删除失败");
        }
        return result;
    }

}
