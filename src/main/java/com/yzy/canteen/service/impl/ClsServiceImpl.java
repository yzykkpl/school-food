package com.yzy.canteen.service.impl;

import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.dataobject.ClsInfo;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.repository.ClsInfoRepository;
import com.yzy.canteen.service.ClsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-22 21:09
 */
@Service
@Slf4j
public class ClsServiceImpl implements ClsService{
    @Autowired
    private ClsInfoRepository repository;
    @Override
    public ClsInfo findById(Integer clsId) {
        Optional<ClsInfo> byId = repository.findById(clsId);
        if(!byId.isPresent()){
            log.error("查询班级，无该班级id，clsId={}",clsId);
            throw new SellException(ResultEnum.CLS_FIND_FAIL);
        }
        return byId.get();
    }

    @Override
    public List<ClsInfo> findAll() {

        return repository.findAll();
    }

    @Override
    public ClsInfo save(ClsInfo clsInfo) {
        ClsInfo result = repository.save(clsInfo);
        return result;
    }

    @Override
    public List<ClsInfo> findAllBySchoolId(Integer schoolId) {
        List<ClsInfo> clsInfoList = repository.findBySchoolId(schoolId);
        return clsInfoList;
    }
}
