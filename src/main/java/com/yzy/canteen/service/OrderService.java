package com.yzy.canteen.service;

import com.yzy.canteen.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {

    /** 创建订单. */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单. */
    OrderDTO findById(String orderId);

    /** 查询订单列表. */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /** 卖家取消订单. */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 买家取消订单. */
    OrderDTO userCancel(OrderDTO orderDTO);
    /** 完结订单. */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单. */
    OrderDTO paid(OrderDTO orderDTO);

    /** 查询订单列表. */
    Page<OrderDTO> findList(Pageable pageable);

    /** 买家申请退款 */
    OrderDTO applyForRefund(OrderDTO orderDTO);
    /** 退款 */
    OrderDTO refund(OrderDTO orderDTO);

    /** 查找订单列表-根据订单状态 */
    Page<OrderDTO> findListByPayStatus(Integer orderStatus,Pageable pageable);
    /** 用户查找订单列表-根据openid和下单时间段 */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable,String start,String end);
    /** 后台查找订单列表-根据预定时间段 */
    Page<OrderDTO> findList(Pageable pageable,String start,String end);
    /** 后台查找订单列表-根据学校+预定时间段 */
    Page<OrderDTO> findList(Pageable pageable,String school,String start,String end);
    /** 后台查找订单列表-根据学校+班级+预定时间段 */
    Page<OrderDTO> findList(Pageable pageable,String school,String cls,String start,String end);



}
