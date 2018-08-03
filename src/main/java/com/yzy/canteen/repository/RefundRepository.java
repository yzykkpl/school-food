package com.yzy.canteen.repository;

import com.yzy.canteen.dataobject.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description:
 * @author: yzy
 * @create: 2018-05-23 21:04
 */
public interface RefundRepository extends JpaRepository<Refund,String>{
    List<Refund> findByOrderId(String orderId);
    List<Refund> findByStatusAndOrderIdIn(Integer status,List<String> orderIdList);
}
