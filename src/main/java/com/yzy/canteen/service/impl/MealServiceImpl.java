package com.yzy.canteen.service.impl;

import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.converter.OrderMeal2MealDTOConverter;
import com.yzy.canteen.dataobject.*;
import com.yzy.canteen.dto.MealDTO;
import com.yzy.canteen.enums.OrderStatusEnum;
import com.yzy.canteen.enums.PayStatusEnum;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.repository.OrderMealRepository;
import com.yzy.canteen.repository.RefundRepository;
import com.yzy.canteen.service.MealInfoService;
import com.yzy.canteen.service.MealPayService;
import com.yzy.canteen.service.MealService;
import com.yzy.canteen.utils.DateUtil;
import com.yzy.canteen.utils.payUtil.model.RefundResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @description: 套餐订单
 * @author: yzy
 * @create: 2018-05-23 19:17
 */
@Service
@Slf4j
public class MealServiceImpl implements MealService {
    @Autowired
    private OrderMealRepository mealRepository;
    @Autowired
    private RefundRepository refundRepository;
    @Autowired
    private MealInfoService mealInfoService;
    @Autowired
    private MealPayService mealPayService;

    @Override
    @Transactional
    public OrderMeal create(OrderMeal orderMeal) {
        OrderMeal result = mealRepository.save(orderMeal);
        return result;
    }

    @Override
    public MealDTO findById(String orderId) {
        OrderMeal orderMeal = mealRepository.findByOrderId(orderId);
        if (orderMeal == null) throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        MealDTO mealDTO = new MealDTO();
        BeanUtils.copyProperties(orderMeal, mealDTO);
        MealInfo mealInfo = mealInfoService.findById(mealDTO.getMealId());
        mealDTO.setMealInfo(mealInfo);
        return mealDTO;
    }

    @Override
    public Page<MealDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMeal> orderMealPage = mealRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<MealDTO> mealDTOList = OrderMeal2MealDTOConverter.convert(orderMealPage.getContent());
        Page<MealDTO> mealDTOPage = new PageImpl<MealDTO>(mealDTOList, pageable, orderMealPage.getTotalElements());
        return mealDTOPage;
    }

    @Override
    public Page<MealDTO> findList(String buyerOpenid, Pageable pageable, String start, String end) {
        Date startDate = DateUtil.getDate(start);
        Date endDate = DateUtil.getDate(end);
        Page<OrderMeal> orderMealPage = mealRepository.findByBuyerOpenidAndCreateTimeBetween(buyerOpenid, pageable, startDate, endDate);

        List<MealDTO> mealDTOList = OrderMeal2MealDTOConverter.convert(orderMealPage.getContent());
        Page<MealDTO> mealDTOPage = new PageImpl<MealDTO>(mealDTOList, pageable, orderMealPage.getTotalElements());
        return mealDTOPage;
    }

    @Override
    public Page<MealDTO> findListWithDate(Pageable pageable, String start, String end) {
        Date startDate = DateUtil.getDate(start);
        Date endDate = DateUtil.getDate(end);
        Page<OrderMeal> orderMealPage = mealRepository.findByCreateTimeBetweenAndPayStatus(startDate, endDate, 1, pageable);
        List<MealDTO> mealDTOList = OrderMeal2MealDTOConverter.convert(orderMealPage.getContent());
        Page<MealDTO> mealDTOPage = new PageImpl<>(mealDTOList, pageable, orderMealPage.getTotalElements());
        return mealDTOPage;
    }

    @Override
    public Page<MealDTO> findListWithSchool(Pageable pageable, String school) {
        Page<OrderMeal> orderMealPage = mealRepository.findByBuyerSchoolAndPayStatus(school, 1, pageable);
        List<MealDTO> mealDTOList = OrderMeal2MealDTOConverter.convert(orderMealPage.getContent());
        Page<MealDTO> mealDTOPage = new PageImpl<>(mealDTOList, pageable, orderMealPage.getTotalElements());
        return mealDTOPage;
    }

    @Override
    public Page<MealDTO> findListWithSchoolAndDate(Pageable pageable, String school, String start, String end) {
        Date startDate = DateUtil.getDate(start);
        Date endDate = DateUtil.getDate(end);
        Page<OrderMeal> orderMealPage = mealRepository.findByBuyerSchoolAndCreateTimeBetweenAndPayStatus(school, startDate, endDate, 1, pageable);
        List<MealDTO> mealDTOList = OrderMeal2MealDTOConverter.convert(orderMealPage.getContent());
        Page<MealDTO> mealDTOPage = new PageImpl<>(mealDTOList, pageable, orderMealPage.getTotalElements());
        return mealDTOPage;
    }

    @Override
    public Page<MealDTO> findListWithSchoolAndCls(Pageable pageable, String school, String cls) {
        Page<OrderMeal> orderMealPage = mealRepository.findByBuyerSchoolAndBuyerClsAndPayStatus(school, cls, 1, pageable);
        List<MealDTO> mealDTOList = OrderMeal2MealDTOConverter.convert(orderMealPage.getContent());
        Page<MealDTO> mealDTOPage = new PageImpl<>(mealDTOList, pageable, orderMealPage.getTotalElements());
        return mealDTOPage;
    }

    @Override
    public Page<MealDTO> findListWithSchoolAndClsAndDate(Pageable pageable, String school, String cls, String start, String end) {
        Date startDate = DateUtil.getDate(start);
        Date endDate = DateUtil.getDate(end);
        Page<OrderMeal> orderMealPage = mealRepository.findByBuyerSchoolAndBuyerClsAndCreateTimeBetweenAndPayStatus(school, cls, startDate, endDate, 1, pageable);
        List<MealDTO> mealDTOList = OrderMeal2MealDTOConverter.convert(orderMealPage.getContent());
        Page<MealDTO> mealDTOPage = new PageImpl<>(mealDTOList, pageable, orderMealPage.getTotalElements());
        return mealDTOPage;
    }

    @Override
    public Page<MealDTO> findListWithSchoolAndMealId(Pageable pageable, String school, String mealId) {
        Page<OrderMeal> orderMealPage = mealRepository.findByBuyerSchoolAndMealIdAndPayStatus(school, mealId, 1, pageable);
        List<MealDTO> mealDTOList = OrderMeal2MealDTOConverter.convert(orderMealPage.getContent());
        Page<MealDTO> mealDTOPage = new PageImpl<>(mealDTOList, pageable, orderMealPage.getTotalElements());
        return mealDTOPage;
    }

    @Override
    public Page<MealDTO> findListWithSchoolAndClsAndMealId(Pageable pageable, String school, String cls, String mealId) {
        Page<OrderMeal> orderMealPage = mealRepository.findByBuyerSchoolAndBuyerClsAndMealIdAndPayStatus(school, cls, mealId, 1, pageable);
        List<MealDTO> mealDTOList = OrderMeal2MealDTOConverter.convert(orderMealPage.getContent());
        Page<MealDTO> mealDTOPage = new PageImpl<>(mealDTOList, pageable, orderMealPage.getTotalElements());
        return mealDTOPage;
    }

    @Override
    public List<MealDTO> findFutureList(String buyerOpenid) {
        List<OrderMeal> futureList = mealRepository.findByBuyerOpenidAndPayStatusAndOrderStatusNot
                (buyerOpenid,
                        PayStatusEnum.SUCCESS.getCode(),
                        OrderStatusEnum.FINISHED.getCode());
        List<MealDTO> mealDTOList = OrderMeal2MealDTOConverter.convert(futureList);
        return mealDTOList;
    }

    @Override
    @Transactional
    public OrderMeal cancel(OrderMeal orderMeal) {
        //TODO
        return null;
    }

    @Override
    @Transactional
    public MealDTO userCancel(MealDTO mealDTO) {
        if (!mealDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("买家取消订单，支付状态错误：orderId={},orderStatus={}", mealDTO.getOrderId(), mealDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改订单状态为取消
        mealDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMeal orderMeal = new OrderMeal();
        BeanUtils.copyProperties(mealDTO, orderMeal);
        OrderMeal updateResult = mealRepository.save(orderMeal);
        if (updateResult == null) {
            log.error("取消订单：orderMeal={},", updateResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return mealDTO;
    }

    @Override
    @Transactional
    public OrderMeal finish(OrderMeal orderMeal) {
        //1 判断订单状态
        if (orderMeal.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())) {
            log.error("完结订单，状态不正确：orderId={},orderStatus={}", orderMeal.getOrderId(), orderMeal.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2 修改订单状态
        orderMeal.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMeal updateResult = mealRepository.save(orderMeal);
        if (updateResult == null) {
            log.error("完结订单：orderMeal={},", updateResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderMeal;
    }

    @Override
    @Transactional
    public MealDTO paid(MealDTO mealDTO) {
        //1 判断订单状态
        if (!mealDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("支付订单，订单状态不正确：orderId={},orderStatus={}", mealDTO.getOrderId(), mealDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2 判断支付状态
        if (!mealDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("支付订单，支付状态不正确：orderId={},payStatus={}", mealDTO.getOrderId(), mealDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //3 修改支付状态
        OrderMeal orderMeal = new OrderMeal();
        mealDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(mealDTO, orderMeal);
        OrderMeal updateResult = mealRepository.save(orderMeal);
        if (updateResult == null) {
            log.error("支付订单：orderMeal={},", updateResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return mealDTO;
    }

    @Override
    public Page<MealDTO> findList(Pageable pageable) {
        Page<OrderMeal> orderMealPage = mealRepository.findAllByPayStatus(1, pageable);
        List<MealDTO> mealDTOList = OrderMeal2MealDTOConverter.convert(orderMealPage.getContent());
        Page<MealDTO> mealDTOPage = new PageImpl<MealDTO>(mealDTOList, pageable, orderMealPage.getTotalElements());
        return mealDTOPage;
    }

    @Override
    public MealDTO applyForRefund(MealDTO mealDTO, Refund refund) {
        //判断订单状态
        if (mealDTO.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())) {
            log.error("申请退款订单：orderId={},orderStatus={}",
                    mealDTO.getOrderId(),
                    mealDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        if (!mealDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            log.error("申请退款,但是支付状态不正确：orderId={},orderStatus={}，payStatus={}",
                    mealDTO.getOrderId(),
                    mealDTO.getOrderStatus(),
                    mealDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态为申请退款
        mealDTO.setOrderStatus(OrderStatusEnum.APPLY_FOR_REFUND.getCode());
        OrderMeal orderMeal = new OrderMeal();
        BeanUtils.copyProperties(mealDTO, orderMeal);
        OrderMeal updateResult = mealRepository.save(orderMeal);
        Refund refundResult = refundRepository.save(refund);
        if (updateResult == null) {
            log.error("申请退款订单：orderMeal={},", updateResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        if (refundResult == null) {
            log.error("申请退款订单：refund={},", refundResult);
            throw new SellException(ResultEnum.REFUND_SAVE_FAIL);
        }
        return mealDTO;
    }


    //后台执行
    @Override
    public MealDTO refund(MealDTO mealDTO, String refundId) {
        OrderMeal orderMeal = mealRepository.findByOrderId(mealDTO.getOrderId());
        //判断订单状态
        if (!orderMeal.getOrderStatus().equals(OrderStatusEnum.APPLY_FOR_REFUND.getCode())) {
            log.error("退款，订单状态不正确：orderId={},orderStatus={}", orderMeal.getOrderId(), orderMeal.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        if (!orderMeal.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            log.error("退款,支付状态不正确：orderId={},orderStatus={}，payStatus={}", orderMeal.getOrderId(), orderMeal.getOrderStatus(), orderMeal.getPayStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }


        //修改订单状态和支付状态
        Refund refund = refundRepository.findById(refundId).get();
        if (refund == null) {
            log.error("退款,退款申请不存在：orderId={},", orderMeal.getOrderId());
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        log.info("执行退款,orderId={},refundId={}", mealDTO.getOrderId(), refund.getRefundId());
        RefundResponse rebateResult = mealPayService.rebate(mealDTO, refund);
        if (rebateResult.getReturnCode().equals("FAIL")||rebateResult.getResultCode().equals("FAIL")) {
            log.error("退款失败：msg={},resultDetail={}", rebateResult.getReturnMsg(),rebateResult.getErrCodeDes());
            throw new SellException(ResultEnum.REFUND_FAIL);
        }

        orderMeal.setOrderStatus(OrderStatusEnum.REFUNDED.getCode());
        refund.setStatus(1);
        OrderMeal mealUpdate = mealRepository.save(orderMeal);
        Refund refundUpdate = refundRepository.save(refund);
        if (mealUpdate == null) {
            log.error("申请退款订单更新错误：orderMeal={},", mealUpdate);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        if (refundUpdate == null) {
            log.error("申请退款订单更新错误：refund={},", refundUpdate);
            throw new SellException(ResultEnum.REFUND_UPDATE_FAIL);
        }

        return mealDTO;
    }

    @Override
    public Page<MealDTO> findListByOrderStatus(Integer orderStatus, Pageable pageable) {
        Page<OrderMeal> orderMealPage = mealRepository.findByOrderStatus(orderStatus, pageable);
        List<MealDTO> mealDTOList = OrderMeal2MealDTOConverter.convert(orderMealPage.getContent());
        Page<MealDTO> mealDTOPage = new PageImpl<>(mealDTOList, pageable, orderMealPage.getTotalElements());
        return mealDTOPage;
    }
}
