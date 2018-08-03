package com.yzy.canteen.service.impl;

import com.yzy.canteen.dataobject.OrderDetail;
import com.yzy.canteen.dto.OrderDTO;
import com.yzy.canteen.enums.OrderStatusEnum;
import com.yzy.canteen.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    OrderServiceImpl orderService;

    private final String BUYER_OPENID="123456789";
    private final String ORDER_ID="1522060938193464668";
    @Test
    public void create() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("dan");
        orderDTO.setBuyerPhone("12121212121");
        orderDTO.setBuyerSchool("bupt");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList= new ArrayList<>();
        OrderDetail o1=new OrderDetail();
        OrderDetail o2=new OrderDetail();

        o1.setProductId("123457");
        o1.setProductQuantity(1);
        o2.setProductQuantity(3);
        o2.setProductId("123456");
        orderDetailList.add(o1);
        orderDetailList.add(o2);
        OrderDTO result = orderService.create(orderDTO);
        log.info("创建result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findById() {
        OrderDTO result=orderService.findById(ORDER_ID);
        log.info("查询订单：result={}",result);
        Assert.assertEquals(ORDER_ID,result.getOrderId());
    }

    @Test
    public void findList() {
        Pageable pageable=PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, pageable);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO =orderService.findById(ORDER_ID);
        OrderDTO result=orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());

    }

    @Test
    public void finish() {
        OrderDTO orderDTO =orderService.findById(ORDER_ID);
        OrderDTO result=orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO =orderService.findById(ORDER_ID);
        OrderDTO result=orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void findList1() {
        Pageable pageable=PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage=orderService.findList(pageable);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }
}