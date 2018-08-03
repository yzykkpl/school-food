package com.yzy.canteen.repository;

import com.yzy.canteen.dataobject.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description: 用户信息表
 * @author: yzy
 * @create: 2018-04-03 17:52
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer>{
    UserInfo findByOpenid(String openid);
    Page<UserInfo> findByStatusAndStdNumNotIn(Integer status, List<String> stdNumList, Pageable pageable);
    Page<UserInfo> findByStatusAndSchoolAndStdNumNotIn(Integer status, String school,List<String> stdNumList, Pageable pageable);

    Page<UserInfo> findBySchoolIdAndNameAndClsAndStatus(Integer schoolId,String name,String cls,Integer status,Pageable pageable);
    Page<UserInfo> findBySchoolIdAndNameAndStatusIn(Integer schoolId,String name,List<Integer> statusList,Pageable pageable);
    Page<UserInfo> findBySchoolIdAndClsAndStatus(Integer schoolId,String cls,Integer status,Pageable pageable);
    Page<UserInfo> findBySchoolIdAndStatusIn(Integer schoolId,List<Integer> statusList,Pageable pageable);
    Page<UserInfo> findByNameAndStatusIn(String name,List<Integer> statusList,Pageable pageable);
    Page<UserInfo> findAllByStatusIsNot(Integer status,Pageable pageable);




}
