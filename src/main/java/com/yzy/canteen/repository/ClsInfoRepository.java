package com.yzy.canteen.repository;

import com.yzy.canteen.dataobject.ClsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-22 20:36
 */
public interface ClsInfoRepository extends JpaRepository<ClsInfo,Integer> {
    List<ClsInfo> findBySchoolId(Integer schoolId);
}
