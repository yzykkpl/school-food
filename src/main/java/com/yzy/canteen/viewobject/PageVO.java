package com.yzy.canteen.viewobject;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-29 22:15
 */
@Data
public class PageVO<T> {
    private Integer pageSize;
    private Integer pageNumber;
    private Long totalRow;
    private Integer totalPage;
    private String mealId="";
    //具体内容
    private List<T> list;
}
