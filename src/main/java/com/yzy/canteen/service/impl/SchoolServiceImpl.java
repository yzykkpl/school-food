package com.yzy.canteen.service.impl;

import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.dataobject.ClsInfo;
import com.yzy.canteen.dataobject.SchoolInfo;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.repository.SchoolInfoRepository;
import com.yzy.canteen.service.ClsService;
import com.yzy.canteen.service.SchoolService;
import com.yzy.canteen.viewobject.SchoolVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-22 20:57
 */
@Service
@Slf4j
public class SchoolServiceImpl implements SchoolService{
    @Autowired
    private SchoolInfoRepository repository;
    @Autowired
    private ClsService clsService;
    @Override
    public SchoolInfo findById(Integer schoolId) {
        Optional<SchoolInfo> byId = repository.findById(schoolId);
        if(!byId.isPresent()){
            log.error("查询学校，无该学校id，schoolId={}",schoolId);
            throw new SellException(ResultEnum.SCHOOL_FIND_FAIL);
        }
        return byId.get();
    }

    @Override
    public List<SchoolInfo> findAll() {
        return repository.findAll();
    }

    @Override
    public SchoolInfo save(SchoolInfo schoolInfo) {
        SchoolInfo result = repository.save(schoolInfo);
        return result;
    }

    @Override
    public List<SchoolVO> findAllWithCls() {
        //1.查询所有学校
        List<SchoolInfo> schoolInfoList =findAll();
        //2.一次查询所有班级
        List<ClsInfo> clsInfoList=clsService.findAll();
        //3.数据拼装
        List<SchoolVO> schoolVOList=new ArrayList<>();
        for(SchoolInfo schoolInfo:schoolInfoList){
            SchoolVO schoolVO=new SchoolVO();
            schoolVO.setSchoolId(schoolInfo.getSchoolId());
            schoolVO.setSchoolName(schoolInfo.getSchoolName());
            List<ClsInfo> clsInfoList2 = new ArrayList<>();
            for(ClsInfo clsInfo:clsInfoList){
                if(clsInfo.getSchoolId()==schoolInfo.getSchoolId()) clsInfoList2.add(clsInfo);
            }
            schoolVO.setClsInfoList(clsInfoList2);

            schoolVOList.add(schoolVO);
        }
        return schoolVOList;
    }


}
