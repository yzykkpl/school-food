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
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
            <#--订单信息-->
                <div class="col-md-4 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>
                                订单ID
                            </th>
                            <th>
                                总金额
                            </th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.orderAmount}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            <#--订单详情-->
                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>商品ID</th>
                            <th>商品名称</th>
                            <th>单价</th>
                            <th>数量</th>
                            <th>总额</th>
                        </tr>
                        </thead>
                        <tbody>
                <#list orderDTO.orderDetailList as productDetail>
                <tr>
                    <td>${productDetail.productId}</td>
                    <td>${productDetail.productName}</td>
                    <td>${productDetail.productPrice}</td>
                    <td>${productDetail.productQuantity}</td>
                    <td>${productDetail.productPrice * productDetail.productQuantity}</td>
                </tr>
                </#list>
                        </tbody>
                    </table>
                </div>
            <#--操作按钮-->

                <div class="col-md-12 column">
             <#if orderDTO.getOrderStatusEnum().message=="新订单">
                 <a href="/canteen/seller/order/finish?orderId=${orderDTO.orderId}" type="button"
                    class="btn btn-default btn-primary">完结订单</a>
             </#if>
             <#if orderDTO.getOrderStatusEnum().message=="申请退款">
                 <a href="/canteen/seller/order/refund?orderId=${orderDTO.orderId}" type="button"
                    class="btn btn-default btn-danger">退款</a>
             </#if>
                </div>

            </div>
        </div>
    </div>
</body>
</html>