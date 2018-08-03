package com.yzy.canteen.service;

import com.yzy.canteen.dataobject.SellerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description: 卖家端
 * @author: yzy
 * @create: 2018-04-01 16:38
 */
public interface SellerService {

    /**
     * 通过openid查找卖家用户
     * @param:
     */

    SellerInfo findSellerInfoByUsername(String username);

    List<SellerInfo> findAll();
    SellerInfo save(String username,String password);
    SellerInfo update(String username,String password);
    void delete(String username);
}
