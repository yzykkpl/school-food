package com.yzy.canteen.controller;

import com.yzy.canteen.dataobject.ClsInfo;
import com.yzy.canteen.dataobject.SchoolInfo;
import com.yzy.canteen.service.ClsService;
import com.yzy.canteen.service.SchoolService;
import com.yzy.canteen.utils.ResultVOUtil;
import com.yzy.canteen.viewobject.ResultVO;
import com.yzy.canteen.viewobject.SchoolVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:查询学校班级信息
 * @author: yzy
 * @create: 2018-05-22 21:15
 */
@RestController
@RequestMapping("/school/")
@Slf4j
public class SchoolController {
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private ClsService clsService;
    @GetMapping("/list")
    public ResultVO list(){
        //1.查询所有学校
        List<SchoolInfo> schoolInfoList = schoolService.findAll();
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
        
        return ResultVOUtil.success(schoolVOList);
    }

}
