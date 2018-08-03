package com.yzy.canteen.repository;

import com.yzy.canteen.dataobject.SellerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @description:
 * @author: yzy
 * @create: 2018-04-01 16:06
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {
    SellerInfo findByUsername(String username);

}
