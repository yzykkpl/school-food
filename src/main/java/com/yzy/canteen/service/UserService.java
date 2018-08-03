package com.yzy.canteen.service;

import com.yzy.canteen.dataobject.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description:
 * @author: yzy
 * @create: 2018-04-03 17:53
 */
public interface UserService {
    UserInfo findUserByOpenid(String openid);

    Page<UserInfo> findStudentNotPaid(String mealId, String school,Pageable pageable);

    Page<UserInfo> findAll(Pageable pageable);
    UserInfo create(String openid,String name);

    List<UserInfo> delete(List<Integer> idList);
}
