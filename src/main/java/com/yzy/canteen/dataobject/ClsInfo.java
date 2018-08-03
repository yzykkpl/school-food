package com.yzy.canteen.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-22 20:31
 */
@Entity
@Data
@DynamicUpdate
public class ClsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clsId;
    @NotEmpty(message = "名称必填")
    private String clsName;
    @NotNull(message = "学校id必填")
    private Integer schoolId;
}
