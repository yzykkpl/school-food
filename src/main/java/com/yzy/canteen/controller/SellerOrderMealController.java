package com.yzy.canteen.controller;

import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;
import com.yzy.canteen.Excetion.SellException;
import com.yzy.canteen.dataobject.OrderMeal;
import com.yzy.canteen.dataobject.Refund;
import com.yzy.canteen.dataobject.UserInfo;
import com.yzy.canteen.dto.MealDTO;
import com.yzy.canteen.enums.ResultEnum;
import com.yzy.canteen.repository.OrderMealRepository;
import com.yzy.canteen.repository.RefundRepository;
import com.yzy.canteen.service.MealService;
import com.yzy.canteen.service.SchoolService;
import com.yzy.canteen.service.UserService;
import com.yzy.canteen.utils.DateUtil;
import com.yzy.canteen.utils.List2StringUtil;
import com.yzy.canteen.utils.SortUtil;
import com.yzy.canteen.viewobject.PageVO;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @description: 卖家端订单
 * @author: yzy
 * @create: 2018-03-30 19:56
 */
@Controller
@Slf4j
@RequestMapping("/seller/mealOrder")
public class SellerOrderMealController {
    @Autowired
    private MealService mealService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private UserService userService;
    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private OrderMealRepository mealRepository;

    //返回初始页面，所有订单
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "pageNumber", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "20") Integer size, Map<String, Object> map) {
        Pageable pageable = PageRequest.of(page - 1, size, SortUtil.basicSort("desc", "createTime"));
        Page<MealDTO> mealDTOPage = mealService.findList(pageable);
        Integer difference = size - mealDTOPage.getContent().size();
        List<SchoolVO> schoolVOList = schoolService.findAllWithCls();
        Gson gson = new Gson();
        String schoolListJson = gson.toJson(schoolVOList);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String start = format.format(c.getTime());
        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String end = format.format(ca.getTime());
        map.put("mealDTOPage", mealDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("difference", difference);
        map.put("schoolList", schoolVOList);
        map.put("schoolListJson", schoolListJson);
        map.put("start",start);
        map.put("end",end);
        ModelAndView model = new ModelAndView("orderMeal/list", map);
        return model;


    }

    /**
     * 按学校+班级查询
     *
     * @param:
     */
    @RequestMapping("/schoolList")
    @ResponseBody
    public PageVO schoolList(@RequestParam("school") String school,
                             @RequestParam(value = "cls", defaultValue = "") String cls,
                             @RequestParam(value = "pageNumber", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer size, Map<String, Object> map) {
        Pageable pageable = PageRequest.of(page-1, size, SortUtil.basicSort("desc", "createTime"));
        Page<MealDTO> mealDTOPage = null;
        if (StringUtils.isNullOrEmpty(cls)) mealDTOPage = mealService.findListWithSchool(pageable, school);
        else mealDTOPage = mealService.findListWithSchoolAndCls(pageable, school, cls);
        List<MealDTO> mealDTOList = mealDTOPage.getContent();
        PageVO pageVO = new PageVO();
        pageVO.setPageNumber(page);
        pageVO.setPageSize(mealDTOPage.getSize());
        pageVO.setTotalPage(mealDTOPage.getTotalPages());
        pageVO.setTotalRow(mealDTOPage.getTotalElements());
        pageVO.setList(mealDTOList);
        return pageVO;
    }

    /**
     * 按学校+班级+日期查询
     *
     * @param:
     */
    @RequestMapping("/schoolDateList")
    @ResponseBody
    public PageVO schoolDateList(@RequestParam("school") String school,
                                 @RequestParam("start") String start,
                                 @RequestParam("end") String end,
                                 @RequestParam(value = "cls", defaultValue = "") String cls,
                                 @RequestParam(value = "pageNumber", defaultValue = "1") Integer page,
                                 @RequestParam(value = "pageSize", defaultValue = "50") Integer size, Map<String, Object> map) {
        Pageable pageable = PageRequest.of(page-1, size, SortUtil.basicSort("desc", "createTime"));
        Page<MealDTO> mealDTOPage = null;
        if (StringUtils.isNullOrEmpty(cls))
            mealDTOPage = mealService.findListWithSchoolAndDate(pageable, school, start, end);
        else mealDTOPage = mealService.findListWithSchoolAndClsAndDate(pageable, school, cls, start, end);
        List<MealDTO> mealDTOList = mealDTOPage.getContent();
        PageVO pageVO = new PageVO();
        pageVO.setPageNumber(page);
        pageVO.setPageSize(mealDTOPage.getSize());
        pageVO.setTotalPage(mealDTOPage.getTotalPages());
        pageVO.setTotalRow(mealDTOPage.getTotalElements());
        pageVO.setList(mealDTOList);
        return pageVO;
    }

    /**
     * 按学校+班级+mealId查询
     *
     * @param:
     */
    @RequestMapping("/schoolMealList")
    @ResponseBody
    public PageVO schoolMealList(@RequestParam("school") String school,
                                 @RequestParam("mealId") String mealId,
                                 @RequestParam(value = "cls", defaultValue = "") String cls,
                                 @RequestParam(value = "pageNumber", defaultValue = "1") Integer page,
                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer size, Map<String, Object> map) {
        Pageable pageable = PageRequest.of(page-1, size, SortUtil.basicSort("desc", "createTime"));
        Page<MealDTO> mealDTOPage = null;
        if (StringUtils.isNullOrEmpty(cls))
            mealDTOPage = mealService.findListWithSchoolAndMealId(pageable, school, mealId);
        else mealDTOPage = mealService.findListWithSchoolAndClsAndMealId(pageable, school, cls, mealId);
        List<MealDTO> mealDTOList = mealDTOPage.getContent();
        PageVO pageVO = new PageVO();
        pageVO.setPageNumber(page);
        pageVO.setPageSize(mealDTOPage.getSize());
        pageVO.setTotalPage(mealDTOPage.getTotalPages());
        pageVO.setTotalRow(mealDTOPage.getTotalElements());
        pageVO.setList(mealDTOList);
        return pageVO;
    }

    /**
     * 按时间段查询
     *
     * @param:
     */
    @RequestMapping("/dateList")
    @ResponseBody
    public PageVO dateList(@RequestParam("start") String start,
                           @RequestParam("end") String end,
                           @RequestParam(value = "pageNumber", defaultValue = "1") Integer page,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer size, Map<String, Object> map) {
        Pageable pageable = PageRequest.of(page-1 , size, SortUtil.basicSort("desc", "createTime"));
        Page<MealDTO> mealDTOPage = mealService.findListWithDate(pageable, start, end);
        List<MealDTO> mealDTOList = mealDTOPage.getContent();
        PageVO pageVO = new PageVO();
        pageVO.setPageNumber(page);
        pageVO.setPageSize(mealDTOPage.getSize());
        pageVO.setTotalPage(mealDTOPage.getTotalPages());
        pageVO.setTotalRow(mealDTOPage.getTotalElements());
        pageVO.setList(mealDTOList);
        return pageVO;
    }
    /**
     * 查询退款订单
     *
     * @param:
     */
    @RequestMapping("/refundList")
    @ResponseBody
    public PageVO refundList(@RequestParam(value = "pageNumber", defaultValue = "1") Integer page,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer size, Map<String, Object> map) {
        Pageable pageable = PageRequest.of(page-1 , size, SortUtil.twoSort("desc", "buyerSchool","desc","createTime"));
        Page<MealDTO> mealDTOPage = mealService.findListByOrderStatus(3, pageable);
        List<MealDTO> mealDTOList = mealDTOPage.getContent();
        PageVO pageVO = new PageVO();
        pageVO.setPageNumber(page);
        pageVO.setPageSize(mealDTOPage.getSize());
        pageVO.setTotalPage(mealDTOPage.getTotalPages());
        pageVO.setTotalRow(mealDTOPage.getTotalElements());
        pageVO.setList(mealDTOList);
        return pageVO;
    }

    /**
     * 查询未支付学生名单
     *
     * @param:
     */
    @RequestMapping("/notPaidList")
    @ResponseBody
    public PageVO notPaidList(@RequestParam("mealId") String mealId,
                            @RequestParam("school") String school,
                            @RequestParam(value = "pageNumber", defaultValue = "1") Integer page,
                             @RequestParam(value = "pageSize", defaultValue = "20") Integer size, Map<String, Object> map) {
        Pageable pageable = PageRequest.of(page-1 , size, SortUtil.twoSort("desc", "school","desc","cls"));
        Page<UserInfo> userInfoPage = userService.findStudentNotPaid(mealId, school,pageable);
        List<UserInfo> userInfoList = userInfoPage.getContent();
        PageVO pageVO = new PageVO();
        pageVO.setPageNumber(page);
        pageVO.setMealId(mealId);
        pageVO.setPageSize(userInfoPage.getSize());
        pageVO.setTotalPage(userInfoPage.getTotalPages());
        pageVO.setTotalRow(userInfoPage.getTotalElements());
        pageVO.setList(userInfoList);
        return pageVO;
    }

    /**
     * 订单详情
     * @param:
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,Map<String,Object> map){
        MealDTO mealDTO;
        try {
            mealDTO = mealService.findById(orderId);

        }catch (NoSuchElementException e){
            log.error("卖家端异常：查询不到订单");
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
            map.put("url","/canteen/seller/mealOrder/list");
            return new ModelAndView("common/error",map);
        }catch (SellException e){
            log.error("卖家端异常={}",e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url","/canteen/seller/mealOrder/list");
            return new ModelAndView("common/error",map);
        }
        List<Refund> refundList=refundRepository.findByOrderId(orderId);
        map.put("mealDTO",mealDTO);
        map.put("refundList",refundList);
        return new ModelAndView("orderMeal/detail",map);
    }



    /**
     * 退款
     * @param:
     */
    @GetMapping("/refund")
    public ModelAndView refund(@RequestParam("orderId") String orderId,
                               @RequestParam("refundId") String refundId,
                               Map<String,Object> map){
        try {
            MealDTO mealDTO = mealService.findById(orderId);
            mealService.refund(mealDTO,refundId);
        }catch (NoSuchElementException e){
            log.error("卖家端退款订单异常：查询不到订单");
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
            map.put("url","/canteen/seller/mealOrder/list");
            return new ModelAndView("common/error",map);
        }catch (SellException e){
            log.error("卖家端退款订单={}",e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url","/canteen/seller/mealOrder/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.REFUND_SUCCESS.getMessage());
        map.put("url","/canteen/seller/mealOrder/detail?orderId="+orderId);
        return new ModelAndView("common/success");
    }

    /**
     * 导出Excel
     * @param:
     */
    @GetMapping("/getExcel")
    public void getExcel(@RequestParam("school") String school,
                         @RequestParam("mealId") String mealId,HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("订单表");
        Sort sort=SortUtil.twoSort("desc","buyerSchool","desc","buyerCls");
        List<OrderMeal> orderMealList = mealRepository.findByBuyerSchoolAndMealIdAndPayStatus(school,mealId,1,sort);
        Map<String, String> refundDate = getRefundDate(orderMealList);
        String fileName = mealId+"list" + ".xlsx";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据

        XSSFCellStyle dateStyle=workbook.createCellStyle();
        XSSFDataFormat fmt = workbook.createDataFormat();
        dateStyle.setDataFormat(fmt.getFormat("yyyy-mm-dd"));
        dateStyle.setAlignment(HorizontalAlignment.LEFT);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(6, 15 * 256);
        sheet.setColumnWidth(8, 10 * 256);
        sheet.setColumnWidth(9, 15 * 256);
        sheet.setColumnWidth(10, 15 * 256);
        sheet.setColumnWidth(11, 20 * 256);
        XSSFCellStyle style=workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        int rowNum = 1;

        String[] headers = { "序号", "订单id", "商品","金额","备注","姓名","学校","班级","学号","手机","创建时间","申请退款日期"};

        XSSFRow row = sheet.createRow(0);

        for(int i=0;i<headers.length;i++){
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            sheet.setDefaultColumnStyle(i,style);
        }
        //在表中存放查询到的数据放入对应的列
        for (OrderMeal orderMeal : orderMealList) {
            XSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(rowNum);
            row1.createCell(1).setCellValue(orderMeal.getOrderId());
            row1.createCell(2).setCellValue(orderMeal.getSnapName());
            row1.createCell(3).setCellValue(orderMeal.getOrderAmount().doubleValue());
            row1.createCell(4).setCellValue(orderMeal.getComment());
            row1.createCell(5).setCellValue(orderMeal.getBuyerName());
            row1.createCell(6).setCellValue(orderMeal.getBuyerSchool());
            row1.createCell(7).setCellValue(orderMeal.getBuyerCls());
            row1.createCell(8).setCellValue(orderMeal.getStdNum());
            row1.createCell(9).setCellValue(orderMeal.getBuyerPhone());
            XSSFCell dateCell = row1.createCell(10);
            dateCell.setCellValue(orderMeal.getCreateTime());
            dateCell.setCellStyle(dateStyle);
            row1.createCell(11).setCellValue(refundDate.get(orderMeal.getOrderId()));
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }
    private Map<String,String> getRefundDate(List<OrderMeal> orderMealList){
        List<String> orderIdList=orderMealList.stream().map(OrderMeal::getOrderId).collect(Collectors.toList());
        List<Refund> refundList = refundRepository.findByStatusAndOrderIdIn(1,orderIdList);
        Map<String,String> result=new HashMap<>();
        Map<String, List<Refund>> refundGroup = refundList.stream().collect(Collectors.groupingBy(Refund::getOrderId));
        refundGroup.forEach((k,v)->{
            String dates=List2StringUtil.getStr(v.stream().map(Refund::getDate).collect(Collectors.toList()));
            result.put(k,dates);
        });

        return result;
    }


//    /**
//     * 完结订单
//     * @param:
//     */
//    @GetMapping("/finish")
//    public ModelAndView finish(@RequestParam("orderId") String orderId,Map<String,Object> map){
//        try {
//            MealDTO orderDTO = mealService.findById(orderId);
//            mealService.finish(orderDTO);
//        }catch (NoSuchElementException e){
//            log.error("卖家端完结订单异常：查询不到订单");
//            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
//            map.put("url","/canteen/seller/orderMeal/list");
//            return new ModelAndView("common/error",map);
//        }catch (SellException e){
//            log.error("卖家端完结订单异常={}",e.getMessage());
//            map.put("msg", e.getMessage());
//            map.put("url","/canteen/seller/orderMeal/list");
//            return new ModelAndView("common/error",map);
//        }
//        map.put("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
//        map.put("url","/canteen/seller/orderMeal/list");
//        return new ModelAndView("common/success");
//    }
//    @GetMapping("/setPay")
//    public ModelAndView setPay(@RequestParam String status,Map<String,Object> map){
//
//        redisTemplate.opsForValue().set("pay",status);
//        map.put("url","/canteen/seller/orderMeal/list");
//        return new ModelAndView("common/success",map);
//    }

}
