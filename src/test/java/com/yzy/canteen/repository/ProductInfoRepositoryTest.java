package com.yzy.canteen.repository;

import com.yzy.canteen.dataobject.ProductInfo;
import com.yzy.canteen.utils.SortUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123455");
        productInfo.setProductName("aaa皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductDescription("good");
        productInfo.setCategoryType(6);
        productInfo.setProductIcon("aaaaa");
        productInfo.setProductStatus(0);

        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList=repository.findByProductStatus(0, SortUtil.basicSort());
        Assert.assertNotEquals(0,productInfoList.size());
    }
}