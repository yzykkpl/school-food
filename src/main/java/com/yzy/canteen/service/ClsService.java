package com.yzy.canteen.service;

import com.yzy.canteen.dataobject.ClsInfo;

import java.util.List;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-22 20:46
 */
public interface ClsService {
    ClsInfo findById(Integer clsId);
    List<ClsInfo> findAll();
    ClsInfo save(ClsInfo clsInfo);
    List<ClsInfo> findAllBySchoolId(Integer schoolId);
}
