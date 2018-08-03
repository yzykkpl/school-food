package com.yzy.canteen.repository;

import com.yzy.canteen.dataobject.ProductInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String>{
    @Query("from ProductInfo o where o.productStatus=:status and (o.schoolId=:id or o.schoolId=-1)")
    List<ProductInfo> findByProductStatusAndSchoolId(@Param("status") Integer productStatus, @Param("id") Integer schoolId, Sort sort);

    List<ProductInfo> findByProductStatus(Integer productStatus, Sort sort);
    List<ProductInfo> findByCategoryType(Integer categoryType);
}
