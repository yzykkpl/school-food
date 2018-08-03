package com.yzy.canteen.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: yzy
 * @create: 2018-04-03 18:05
 */
@Data
public class UserForm {

    @NotEmpty(message = "token必填")
    private String token;

    private String nickName;

    @NotEmpty(message = "名字必填")
    private String name;

    @NotEmpty(message = "学号必填")
    private String stdNum;

    @NotEmpty(message = "手机必填")
    private String phone;

    @NotEmpty(message = "学校必填")
    private String school;
    @NotNull(message = "学校id必填")
    private Integer schoolId;

    @NotEmpty(message = "班级必填")
    private String cls;

    @NotNull(message = "身份必填")
    private Integer status;
}
