package com.yzy.canteen.repository;

import com.yzy.canteen.dataobject.MealInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-23 22:01
 */
public interface MealInfoRepository extends JpaRepository<MealInfo,String>{
    @Query("from MealInfo o where o.mealStatus=:status and (o.schoolId=:id or o.schoolId=-1)")
    List<MealInfo> findByMealStatusAndSchoolId(@Param("status") Integer mealStatus,@Param("id") Integer schoolId);

    List<MealInfo> findByMealStatus(Integer mealStatusm);

}
