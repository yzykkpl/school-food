package com.yzy.canteen.controller;

import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.dto.OrderDTO;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.form.OrderForm;
import com.yzy.canteen.service.BuyerService;
import com.yzy.canteen.service.OrderService;
import com.yzy.canteen.service.ProductService;
import com.yzy.canteen.utils.DateUtil;
import com.yzy.canteen.utils.ResultVOUtil;
import com.yzy.canteen.utils.SortUtil;
import com.yzy.canteen.viewobject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 买家订单
 * @author: yzy
 * @create: 2018-03-26 18:52
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private ProductService productService;
    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("创建订单，参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }
        String openid=buyerService.getOpenid(orderForm.getToken());
        if(openid.equals("e")){
            return ResultVOUtil.error(-1,"relogin");
        }
        orderForm.setBuyerOpenid(openid);
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderForm,orderDTO);
        orderDTO.setDate(DateUtil.getDate(orderForm.getDate()));
        OrderDTO createResult=orderService.create(orderDTO);

        Map<String,String> map=new HashMap<>();
        map.put("orderId",createResult.getOrderId());

        return ResultVOUtil.success(map);
    }
    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("token") String token,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "5") Integer size){
        String openid=buyerService.getOpenid(token);
        if(openid.equals("e")){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Pageable pageable=PageRequest.of(page,size,SortUtil.basicSort("desc", "createTime"));
        Page<OrderDTO> orderDTOPage=orderService.findList(openid,pageable);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    @GetMapping("/dateList")
    public ResultVO<List<OrderDTO>> list(@RequestParam("token") String token,
                                          @RequestParam("start") String start,
                                          @RequestParam("end") String end,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "5") Integer size){
        String openid=buyerService.getOpenid(token);
        if(openid.equals("e")){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Pageable pageable=PageRequest.of(page,size,SortUtil.basicSort("desc", "createTime"));
        Page<OrderDTO> orderDTOPage=orderService.findList(openid,pageable,start,end);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }




    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail (@RequestParam("token") String token,
                                      @RequestParam("orderId") String orderId){
        String openid=buyerService.getOpenid(token);
        if(openid.equals("e")){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO=buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }
    //申请退款
    //用户暂不可自动退款
    @PostMapping("/refund")
    public ResultVO refund (@RequestParam("token") String token,
                                      @RequestParam("orderId") String orderid){
        String openid=buyerService.getOpenid(token);
        if(openid.equals("e")){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.refundOrderOne(openid,orderid);
        return ResultVOUtil.success();
    }

    //取消未支付订单
    //用户暂不可自动退款
    @PostMapping("/cancel")
    public ResultVO userCancel (@RequestParam("token") String token,
                            @RequestParam("orderId") String orderid){
        String openid=buyerService.getOpenid(token);
        if(openid.equals("e")){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cancelOrderOne(openid,orderid);
        return ResultVOUtil.success();
    }
}
