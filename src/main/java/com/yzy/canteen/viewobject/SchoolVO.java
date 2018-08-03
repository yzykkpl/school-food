package com.yzy.canteen.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yzy.canteen.dataobject.ClsInfo;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-22 21:20
 */
@Data
public class SchoolVO {
    @JsonProperty("id")
    private Integer schoolId;

    @JsonProperty("name")
    private String schoolName;

    @JsonProperty("cls")
    private List<ClsInfo> clsInfoList;
}
