<html>
<head>
       <meta charset="utf-8">
       <title>商品列表</title>
       <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
       <link href="/canteen/css/style.css" rel="stylesheet">
    </head>
    <body>
    <#assign total = mealInfoPage.getTotalPages()>
    <div id="wrapper" class="toggled">
        <#--边栏-->
            <#include "../common/nav.ftl">
        <#--内容区-->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>商品ID</th>
                                <th>名称</th>
                                <th>图片</th>
                                <th>单价</th>
                                <th>描述</th>
                                <th>对应学校</th>
                                <th>截止日期</th>
                                <th>类目</th>
                                <th>创建时间</th>
                                <th>修改时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                    <#list mealInfoPage.content as mealInfo>
                    <tr>
                        <td>${mealInfo.mealId}</td>
                        <td>${mealInfo.mealName}</td>
                        <td style="text-align:center" width="105"><img height="100" width="100" src="${mealInfo.mealIcon}" ></td>
                        <td>${mealInfo.mealPrice}</td>
                        <td>${mealInfo.mealDescription}</td>
                        <td>${mealInfo.schoolName}</td>
                        <td>${mealInfo.mealDate?string('yyyy-MM-dd')}</td>
                        <td>套餐</td>
                        <td>${mealInfo.createTime}</td>
                        <td>${mealInfo.updateTime}</td>
                        <td ><a href="/canteen/seller/meal/index?mealId=${mealInfo.mealId}">修改</a></td>
                        <td >
                             <#if mealInfo.getMealStatusEnum().message=="在架">
                                 <a href="/canteen/seller/meal/off_sale?mealId=${mealInfo.mealId}">下架</a>
                                 <#elseif mealInfo.getMealStatusEnum().message=="下架">
                                 <a href="/canteen/seller/meal/on_sale?mealId=${mealInfo.mealId}">上架</a>
                             </#if>
                        </td>
                    </tr>
                    </#list>
                    <#if (difference gt 0)>
                        <#list 1..difference as t>
                          <tr>
                              <td>-</td>
                              <td>-</td>
                              <td>-</td>
                              <td>-</td>
                              <td>-</td>
                              <td>-</td>
                              <td>-</td>
                              <td>-</td>
                              <td>-</td>
                              <td>-</td>
                          </tr>
                        </#list>
                    </#if>
                            </tbody>
                        </table>
                    </div>
                <#--分页-->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                    <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                    <li ><a href="/canteen/seller/meal/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>
                    <#if total lte 10>
                        <#list 1..total as index>
                            <#if currentPage==index>
                    <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                    <li><a href="/canteen/seller/meal/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>

                    <#elseif total gte 11>
                    <#--当前页小于4-->
                        <#if currentPage lt 5>
                            <#list 1..5 as index>
                                <#if currentPage==index>
                             <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                             <li><a href="/canteen/seller/meal/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>
                            </#list>
                    <li class="disabled"><a href="#">...</a></li>
                    <li ><a href="/canteen/seller/order/list?page=${total}&size=${size}">${total}</a></li>
                        </#if>
                    <#--当前页大于4小于等于total-4-->
                        <#if (currentPage gte 5)&&(currentPage lt total-3)>
                    <li ><a href="/canteen/seller/order/list?page=1&size=${size}">1</a></li>
                    <li class="disabled"><a href="#">...</a></li>
                            <#list currentPage-2..currentPage+2 as index>
                                <#if currentPage==index>
                             <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                              <li><a href="/canteen/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>
                            </#list>
                    <li class="disabled"><a href="#">...</a></li>
                    <li><a href="/canteen/seller/order/list?page=${total}&size=${size}">${total}</a></li>
                        </#if>
                    <#--当前页大于total-4-->
                        <#if currentPage gte total-3>
                    <li ><a href="/canteen/seller/order/list?page=1&size=${size}">1</a></li>
                    <li class="disabled"><a href="#">...</a></li>
                            <#list total-4..total as index>
                                <#if currentPage==index>
                             <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                             <li><a href="/canteen/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>
                            </#list>

                        </#if>
                    </#if>
                    <#if currentPage gte mealInfoPage.getTotalPages()>
                    <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                    <li ><a href="/canteen/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    </body>
</html>
