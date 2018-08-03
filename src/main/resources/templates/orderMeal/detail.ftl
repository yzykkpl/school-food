<html>
<head>
    <meta charset="utf-8">
    <title>订单详情</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="/canteen/css/style.css" rel="stylesheet">

</head>
<body>

<div id="wrapper" class="toggled">
<#--边栏-->
            <#include "../common/nav.ftl">
<#--内容区-->
    <#assign mealInfo=mealDTO.mealInfo >
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
            <#--订单信息-->
                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>订单ID</th>
                            <th>套餐ID</th>
                            <th>商品名称</th>
                            <th>总天数</th>
                            <th>总金额</th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${mealDTO.orderId}</td>
                            <td>${mealInfo.mealId}</td>
                            <td>${mealInfo.mealName}</td>
                            <td>${mealInfo.days}</td>
                            <td>${mealDTO.orderAmount}</td>

                        </tr>
                        </tbody>
                    </table>
                </div>
            <#--退款单列表-->
                <div class="col-md-10 column">
                    <table class="table table-bordered" style="word-break:break-all; word-wrap:break-all;">
                        <thead>
                        <tr>
                            <th class="col-md-2 text-center">退款单ID</th>
                            <th class="col-md-2 text-center">退款原因</th>
                            <th class="col-md-3 text-center">退款日期</th>
                            <th class="col-md-1 text-center">退款天数</th>
                            <th class="col-md-1 text-center">退款总额</th>
                            <th class="col-md-1 text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list refundList as refund>
                        <tr>
                            <td class="col-md-2" >${refund.refundId}</td>
                            <td class="col-md-2">${refund.reason}</td>
                            <td class="col-md-3">${refund.date}</td>
                            <td class="col-md-1 text-center">${refund.days}</td>
                            <td class="col-md-1 text-center">${refund.price}</td>
                            <#if refund.status==0>
                            <td class="col-md-1">
                                <a href="/canteen/seller/mealOrder/refund?orderId=${refund.orderId}&refundId=${refund.refundId}">退款</a>
                            </td>
                            <#else>
                                <td class="col-md-1 text-center">已退款</td>
                            </#if>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>



            </div>
        </div>
    </div>
</body>
</html>