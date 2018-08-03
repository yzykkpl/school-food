package com.yzy.canteen.handler;

import com.yzy.canteen.Excetion.SellerAuthorizeException;
import com.yzy.canteen.utils.RSAUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: yzy
 * @create: 2018-04-02 11:59
 */
@ControllerAdvice
public class SellExceptionHandler {
    @ExceptionHandler({SellerAuthorizeException.class})
    public ModelAndView handlerAuthorizeException(){
        Map<String,String> map=new HashMap<>();
        map.put("publicKey",RSAUtil.generateBase64PublicKey());
        return new ModelAndView("common/login",map);
    }

//    @ExceptionHandler({SellException.class})
//    @ResponseBody
//    public ResultVO handleSellException(SellException e){
//        return ResultVOUtil.error(e.getCode(),e.getMessage());
//    }
}
