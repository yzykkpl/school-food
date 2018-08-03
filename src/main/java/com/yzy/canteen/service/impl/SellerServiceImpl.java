package com.yzy.canteen.service.impl;

import com.yzy.canteen.dataobject.SellerInfo;
import com.yzy.canteen.repository.SellerInfoRepository;
import com.yzy.canteen.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: yzy
 * @create: 2018-04-01 16:40
 */
@Service
public class SellerServiceImpl implements SellerService{
    @Autowired
    private SellerInfoRepository repository;


    @Override
    public SellerInfo findSellerInfoByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<SellerInfo> findAll() {
        return repository.findAll();
    }

    @Override
    public SellerInfo save(String username, String password) {
        if(repository.findByUsername(username)!=null) return null;
        SellerInfo sellerInfo=new SellerInfo();
        sellerInfo.setUsername(username);
        sellerInfo.setPassword(password);
        return repository.save(sellerInfo);
    }

    @Override
    public SellerInfo update(String username, String password) {
        SellerInfo sellerInfo = repository.findByUsername(username);
        sellerInfo.setPassword(password);
        return repository.save(sellerInfo);
    }

    @Override
    public void delete(String username) {
        SellerInfo sellerInfo=repository.findByUsername(username);
        repository.delete(sellerInfo);
    }
}
