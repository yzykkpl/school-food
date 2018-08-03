package com.yzy.canteen.controller;

import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;
import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.dataobject.OrderMaster;
import com.yzy.canteen.dto.OrderDTO;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.repository.OrderMasterRepository;
import com.yzy.canteen.service.OrderService;
import com.yzy.canteen.service.SchoolService;
import com.yzy.canteen.utils.DateUtil;
import com.yzy.canteen.utils.SortUtil;
import com.yzy.canteen.viewobject.SchoolVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @description: 卖家端订单
 * @author: yzy
 * @create: 2018-03-30 19:56
 */
@Controller
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private OrderMasterRepository repository;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "filter",defaultValue = "1") Integer filter,
                            @RequestParam(value="page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "20") Integer size, Map<String,Object> map){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        return new ModelAndView("redirect:dateList?start="+first+"&end="+last);
//        Pageable pageable= PageRequest.of(page-1,size, SortUtil.basicSort("desc", "createTime"));
//        Page<OrderDTO> orderDTOPage=null;
//        if(filter==-1)
//            orderDTOPage=orderService.findList(pageable);
//        else
//            orderDTOPage=orderService.findListByPayStatus(filter,pageable);
//        Integer difference=size-orderDTOPage.getContent().size();
//        String status=redisTemplate.opsForValue().get("pay");
//        List<SchoolVO> schoolVOList = schoolService.findAllWithCls();
//        Gson gson=new Gson();
//        String schoolListJson = gson.toJson(schoolVOList);
//        map.put("orderDTOPage",orderDTOPage);
//        map.put("currentPage",page);
//        map.put("size",size);
//        map.put("difference",difference);
//        map.put("filter",filter);
//        map.put("status",status);
//        map.put("schoolList",schoolVOList);
//        map.put("school",schoolVOList.get(0).getSchoolName());
//        map.put("schoolListJson",schoolListJson);
//
//        ModelAndView model =new ModelAndView("order/list",map);


        //return model;

    }
    /** 按预定日期查询
     * @param: start:开始日期
     *          end:截止日期
     */
    @GetMapping("/dateList")
    public ModelAndView list(@RequestParam("start") String start,
                             @RequestParam("end") String end,
                             @RequestParam(value = "school",defaultValue="") String school,
                             @RequestParam(value = "cls",defaultValue="") String cls,
                             @RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "20") Integer size,Map<String,Object> map){
        Pageable pageable= PageRequest.of(page-1,size, SortUtil.basicSort("desc", "createTime"));
        Page<OrderDTO> orderDTOPage=null;
        if(StringUtils.isNullOrEmpty(start)||StringUtils.isNullOrEmpty(end)) {
            map.put("msg", "时间段必填");
            map.put("url","/canteen/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        if(StringUtils.isNullOrEmpty(school)) {
            orderDTOPage = orderService.findList(pageable, start, end);
        }else{
            if(StringUtils.isNullOrEmpty(cls))  orderDTOPage = orderService.findList(pageable, school,start, end);
            else  orderDTOPage = orderService.findList(pageable, school,cls,start, end);
        }
        Integer difference=size-orderDTOPage.getContent().size();
        List<SchoolVO> schoolVOList = schoolService.findAllWithCls();
        Gson gson=new Gson();
        String schoolListJson = gson.toJson(schoolVOList);

        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        map.put("difference",difference);
        map.put("start",start);
        map.put("end",end);
        map.put("schoolList",schoolVOList);
        map.put("school",school);
        map.put("cls",cls);
        map.put("schoolListJson",schoolListJson);
        ModelAndView model =new ModelAndView("order/list",map);
        return model;
    }


    /**
     * 取消订单
     * @param:
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,Map<String,Object> map){
        try {
            OrderDTO orderDTO = orderService.findById(orderId);
            orderService.cancel(orderDTO);
        }catch (NoSuchElementException e){
            log.error("卖家端取消订单异常：查询不到订单");
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
            map.put("url","/canteen/seller/order/list");
            return new ModelAndView("common/error",map);
        }catch (SellException e){
            log.error("卖家端取消订单异常={}",e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url","/canteen/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/canteen/seller/order/list");
        return new ModelAndView("common/success");
    }
    /**
     * 退款
     * @param:
     */
    @GetMapping("/refund")
    public ModelAndView refund(@RequestParam("orderId") String orderId,Map<String,Object> map){
        try {
            OrderDTO orderDTO = orderService.findById(orderId);
            orderService.refund(orderDTO);
        }catch (NoSuchElementException e){
            log.error("卖家端退款订单异常：查询不到订单");
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
            map.put("url","/canteen/seller/order/list");
            return new ModelAndView("common/error",map);
        }catch (SellException e){
            log.error("卖家端退款订单异常={}",e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url","/canteen/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/canteen/seller/order/list");
        return new ModelAndView("common/success");
    }
    /**
     * 订单详情
     * @param:
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,Map<String,Object> map){
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findById(orderId);

        }catch (NoSuchElementException e){
            log.error("卖家端异常：查询不到订单");
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
            map.put("url","/canteen/seller/order/list");
            return new ModelAndView("common/error",map);
        }catch (SellException e){
            log.error("卖家端异常={}",e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url","/canteen/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO",orderDTO);
        return new ModelAndView("order/detail",map);
    }
    /**
     * 完结订单
     * @param:
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,Map<String,Object> map){
        try {
            OrderDTO orderDTO = orderService.findById(orderId);
            orderService.finish(orderDTO);
        }catch (NoSuchElementException e){
            log.error("卖家端完结订单异常：查询不到订单");
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
            map.put("url","/canteen/seller/order/list");
            return new ModelAndView("common/error",map);
        }catch (SellException e){
            log.error("卖家端完结订单异常={}",e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url","/canteen/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url","/canteen/seller/order/list");
        return new ModelAndView("common/success");
    }
    @GetMapping("/setPay")
    public ModelAndView setPay(@RequestParam String status,Map<String,Object> map){

        redisTemplate.opsForValue().set("pay",status);
        map.put("url","/canteen/seller/order/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 导出Excel
     * @param:
     */
    @GetMapping("/getExcel")
    public void getExcel(@RequestParam("start") String start,
                                 @RequestParam("end") String end,HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("订单表");
        Date startDate=DateUtil.getDate(start);
        Date endDate=DateUtil.getDate(end);
        Sort sort=SortUtil.twoSort("desc","buyerSchool","desc","date");
        List<OrderMaster> orderMasterList = repository.findByPayStatusAndDateBetween(1,startDate,endDate,sort);

        String fileName = start+"-"+end +"list" + ".xlsx";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        XSSFCellStyle dateStyle=workbook.createCellStyle();
        XSSFDataFormat fmt = workbook.createDataFormat();
        dateStyle.setDataFormat(fmt.getFormat("yyyy-mm-dd"));
        dateStyle.setAlignment(HorizontalAlignment.LEFT);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(5, 15 * 256);
        sheet.setColumnWidth(10, 10 * 256);
        sheet.setColumnWidth(11, 20 * 256);
        sheet.setColumnWidth(12, 20 * 256);
        XSSFCellStyle style=workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        int rowNum = 1;

        String[] headers = { "序号", "订单id", "商品","数量","金额","预定日期","备注","姓名","学校","班级","学号","手机","创建时间"};

        XSSFRow row = sheet.createRow(0);

        for(int i=0;i<headers.length;i++){
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            sheet.setDefaultColumnStyle(i,style);
        }
        //在表中存放查询到的数据放入对应的列
        for (OrderMaster orderMaster : orderMasterList) {
            XSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(rowNum);
            row1.createCell(1).setCellValue(orderMaster.getOrderId());
            row1.createCell(2).setCellValue(orderMaster.getSnapName());
            row1.createCell(3).setCellValue(orderMaster.getCounts());
            row1.createCell(4).setCellValue(orderMaster.getOrderAmount().doubleValue());
            XSSFCell dateCell = row1.createCell(5);
            dateCell.setCellValue(orderMaster.getDate());
            dateCell.setCellStyle(dateStyle);
            row1.createCell(6).setCellValue(orderMaster.getComment());
            row1.createCell(7).setCellValue(orderMaster.getBuyerName());
            row1.createCell(8).setCellValue(orderMaster.getBuyerSchool());
            row1.createCell(9).setCellValue(orderMaster.getBuyerCls());
            row1.createCell(10).setCellValue(orderMaster.getStdNum());
            row1.createCell(11).setCellValue(orderMaster.getBuyerPhone());
            XSSFCell createDateCell = row1.createCell(12);
            createDateCell.setCellValue(orderMaster.getCreateTime());
            createDateCell.setCellStyle(dateStyle);

            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }


}
