package com.yzy.canteen.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-22 20:28
 */
@Entity
@Data
@DynamicUpdate
public class SchoolInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schoolId;
    @NotEmpty(message = "名称必填")
    private String schoolName;

}
