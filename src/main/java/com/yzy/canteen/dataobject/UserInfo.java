package com.yzy.canteen.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @description: 用户信息表
 * @author: yzy
 * @create: 2018-04-03 17:48
 */
@Data
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String openid;
    //微信昵称
    private String nickName;
    //姓名
    private String name;
    //学号
    private String stdNum;
    //电话
    private String phone;
    //学校
    private String school;

    private Integer schoolId;
    //班级
    private String cls;
    //身份 老师1 学生0
    private Integer status;
    public UserInfo() {
    }
}
