package com.yzy.canteen.service.impl;

import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.dataobject.ProductInfo;
import com.yzy.canteen.dto.CartDTO;
import com.yzy.canteen.enums.ProductStatusEnum;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.repository.ProductInfoRepository;
import com.yzy.canteen.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: yzy
 * @create: 2018-03-25 10:21
 */
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findById(String productId) {
        return repository.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findUpAll(Sort sort) {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode(),sort);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        /*for(CartDTO cartDTO:cartDTOList){
           ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
           Integer stock = productInfo.getProductStock();
           productInfo.setProductStock(stock+cartDTO.getProductQuantity());
           repository.save(productInfo);
        }*/
    }


    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
       /* for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo=repository.findById(cartDTO.getProductId()).get();
            if(productInfo==null) throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result<0) throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            //如果库存为0，则下架商品
            else if(result.equals(0)){
                productInfo.setProductStatus(1);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);

        }*/
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo=repository.findById(productId).get();
        if(productInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum()==ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());

        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo=repository.findById(productId).get();
        if(productInfo==null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum()==ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());

        return repository.save(productInfo);
    }
}
