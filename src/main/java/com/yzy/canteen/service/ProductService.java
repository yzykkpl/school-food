package com.yzy.canteen.service;

import com.yzy.canteen.dataobject.ProductInfo;
import com.yzy.canteen.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {
    ProductInfo findById(String productId);

    /**
     * 查询在架商品列表
     */
    List<ProductInfo> findUpAll(Sort sort);

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);
}
