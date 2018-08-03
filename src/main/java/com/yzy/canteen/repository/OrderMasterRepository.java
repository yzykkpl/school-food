package com.yzy.canteen.repository;

import com.yzy.canteen.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String>, JpaSpecificationExecutor<OrderMaster> {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

    Page<OrderMaster> findByPayStatus(Integer orderStatus, Pageable pageable);

    List<OrderMaster> findByBuyerPhone(String buyerPhone);

    //Page<OrderMaster> findByBuyerOpenidAll(String buyerOpenid, Pageable pageable,Specification<OrderMaster> var1);

    Page<OrderMaster> findByBuyerOpenidAndCreateTimeBetween(String buyerOpenid, Pageable pageable, Date start, Date end);

    //    @Query("from OrderMaster o where o.payStatus=:status and ((o.startDate between :startDate and :endDate) or (o.startDate between :startDate and :endDate) or (o.startDate < :startDate and o.endDate >:endDate))")
//    Page<OrderMaster> findByDateParams(@Param("status") Integer payStatus,@Param("startDate") Date start,@Param("endDate") Date end,Pageable pageable);
//
//    @Query("from OrderMaster o where o.payStatus=:status and o.buyerSchool=:school and ((o.startDate between :startDate and :endDate) or (o.startDate between :startDate and :endDate) or (o.startDate < :startDate and o.endDate >:endDate))")
//    Page<OrderMaster> findBySchoolParams(@Param("status") Integer payStatus,@Param("school") String school,@Param("startDate") Date start,@Param("endDate") Date end,Pageable pageable);
//
//    @Query("from OrderMaster o where o.payStatus=:status and o.buyerSchool=:school and o.buyerCls=:cls and ((o.startDate between :startDate and :endDate) or (o.startDate between :startDate and :endDate) or (o.startDate < :startDate and o.endDate >:endDate))")
//    Page<OrderMaster> findBySchoolAndClsParams(@Param("status") Integer payStatus,@Param("school") String school,@Param("cls") String cls,@Param("startDate") Date start,@Param("endDate") Date end,Pageable pageable);
    @Query("from OrderMaster o where o.payStatus=:status and o.date between :startDate and :endDate")
    Page<OrderMaster> findByDateParams(@Param("status") Integer payStatus, @Param("startDate") Date start, @Param("endDate") Date end, Pageable pageable);

    @Query("from OrderMaster o where o.payStatus=:status and o.buyerSchool=:school and o.date between :startDate and :endDate")
    Page<OrderMaster> findBySchoolParams(@Param("status") Integer payStatus, @Param("school") String school, @Param("startDate") Date start, @Param("endDate") Date end, Pageable pageable);

    @Query("from OrderMaster o where o.payStatus=:status and o.buyerSchool=:school and o.buyerCls=:cls and o.date between :startDate and :endDate")
    Page<OrderMaster> findBySchoolAndClsParams(@Param("status") Integer payStatus, @Param("school") String school, @Param("cls") String cls, @Param("startDate") Date start, @Param("endDate") Date end, Pageable pageable);

    List<OrderMaster> findByPayStatusAndDateBetween(Integer payStatus,Date start,Date end,Sort sort);
}
