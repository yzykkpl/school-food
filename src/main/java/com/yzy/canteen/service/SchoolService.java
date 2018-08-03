package com.yzy.canteen.service;

import com.yzy.canteen.dataobject.SchoolInfo;
import com.yzy.canteen.viewobject.SchoolVO;

import java.util.List;

public interface SchoolService {
    SchoolInfo findById(Integer schoolId);
    List<SchoolInfo> findAll();
    SchoolInfo save(SchoolInfo schoolInfo);

    List<SchoolVO> findAllWithCls();

}
