<html>
<head>
    <meta charset="utf-8">
    <title>后台管理</title>
    <link href="/canteen/css/b.page.bootstrap3.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/jquery-impromptu/6.2.3/jquery-impromptu.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.0.0/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery-impromptu/6.2.3/jquery-impromptu.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/canteen/js/load.js"></script>
    <script type="text/javascript" src="/canteen/js/b.page.js"></script>


    <link href="/canteen/css/style.css" rel="stylesheet">
</head>
<body>
    <#assign totalPage = mealDTOPage.getTotalPages()>
    <#assign total = mealDTOPage.getTotalElements()>
<div id="wrapper" class="toggled">
<#--边栏-->
            <#include "../common/nav.ftl">
<#--内容区-->

    <div id="page-content-wrapper">

        <div class="container-fluid">

            <div class="row clearfix">

                <div class="col-md-12 column">

                    <div class="row">
                    <#--<div class="col-xs-2">-->

                    <#--<li class="dropdown btn btn-default" style="margin-bottom: 30px">-->
                    <#--<a href="#" class="dropdown-toggle" style="color: black" data-toggle="dropdown">-->
                    <#--按订单状态筛选-->
                    <#--<b class="caret"></b>-->
                    <#--</a>-->
                    <#--<ul class="dropdown-menu">-->
                    <#--<li><a href="/canteen/seller/mealOrder/list?filter=-1">全部</a></li>-->
                    <#--<li><a href="/canteen/seller/mealOrder/list?filter=0">新订单</a></li>-->
                    <#--<li><a href="/canteen/seller/mealOrder/list?filter=1">已完结</a></li>-->
                    <#--<li><a href="/canteen/seller/mealOrder/list?filter=2">已取消</a></li>-->
                    <#--</ul>-->
                    <#--</li>-->
                    <#--</div>-->
                        <div class="col-xs-10">

                            <form role="form" class="form-inline" method="get" action="#">
                                <label style="color: blue;font-size: 15px;margin-right: 3px">条件筛选:</label>
                                <div class="form-group">
                                    <label for="schSelect">学校</label>
                                    <select name="school" id="schSelect" value='${school!''}' class="form-control"
                                            onchange="findCls()"/>
                                    <option value="">--请选择--</option>
                                        <#list schoolList as sch>
                                            <#assign index=sch_index/>
                                            <option value=${sch.schoolName} id=${index}
                                                    <#if (((school)!'') == sch.schoolName)>selected="selected"</#if> >${sch.schoolName}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="clsSelect">班级</label>
                                    <select name="cls" id="clsSelect" value='${cls!''}' class="form-control"/>
                                    <option value="">--请选择--</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="start">开始时间</label>
                                    <input name="start" id="start" type="date" value='${start}' class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <label for="end">截止时间</label>
                                    <input name="end" id="end" type="date" value='${end}' class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <label for="mealId">套餐ID</label>
                                    <input name="end" id="mealId" type="text" class="form-control"/>
                                </div>
                                <button type="button" onclick="select()" class="btn btn-default">查询</button>
                            </form>

                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <button onclick="getRefundPage()">管理退款订单</button>
                            <button onclick="notPaid()">未支付学生名单</button>
                            <button onclick="getExcel()">按学校和套餐导出Excel</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row clearfix">
                <div id="page1" class="col-md-12 column">

                    <table id="table" class="table table-bordered table-condensed"
                           style="word-break:break-all; word-wrap:break-all;">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>订单ID</th>
                            <th>商品</th>
                            <th>金额</th>
                            <th class="col-md-2">备注</th>
                            <th>姓名</th>
                            <th>学校</th>
                            <th>班级</th>
                            <th>学号</th>
                            <th>手机号</th>
                            <th>创建时间</th>
                            <th>详情</th>
                            <th>退款</th>
                        </tr>
                        </thead>
                        <tbody>
                    <#list mealDTOPage.content as mealDTO>
                    <tr>
                        <td>${size*(currentPage-1)+mealDTO_index+1}</td>
                        <td>${mealDTO.orderId}</td>
                        <td>${mealDTO.snapName}</td>
                        <td>${mealDTO.orderAmount}</td>
                        <td>${mealDTO.comment!""}</td>
                        <td>${mealDTO.buyerName}</td>
                        <td>${mealDTO.buyerSchool}</td>
                        <td>${mealDTO.buyerCls}</td>
                        <td>${mealDTO.stdNum}</td>
                        <td>${mealDTO.buyerPhone}</td>

                    <#--<td>${mealDTO.getPayStatusEnum().getMessage()}</td>-->
                        <td>${mealDTO.createTime}</td>
                        <td>
                            <a href='/canteen/seller/mealOrder/detail?orderId=${mealDTO.orderId}'>查看</a>
                        </td>
                        <td>
                            <#if mealDTO.orderStatus==3>
                                <a href='/canteen/seller/mealOrder/detail?orderId=${mealDTO.orderId}'>申请</a>
                            <#elseif mealDTO.orderStatus==4>
                                已退款
                            <#else>
                                无
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
                              <td>-</td>
                              <td>-</td>
                              <td>-</td>

                          </tr>
                        </#list>
                    </#if>
                        </tbody>
                    </table>

                    <table id="table2" hidden="hidden" class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>学校</th>
                            <th>班级</th>
                            <th>姓名</th>
                            <th>学号</th>
                            <th>手机号</th>
                            <th>套餐名称</th>
                        </tr>
                        </thead>

                        <tbody>

                        </tbody>
                    </table>


                </div>
            </div>



        <#--分页-->
            <div id="myPage" class="col-md-12 column">
                <div class="col-xs-6">订单总数:${total}</div>

                <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                    <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                    <li>
                        <a href="/canteen/seller/mealOrder/list?pageNumber=${currentPage - 1}&size=${size}">上一页</a>
                    </li>
                    </#if>
                    <#if totalPage lte 10>
                        <#list 1..totalPage as index>
                            <#if currentPage==index>
                    <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                    <li><a href="/canteen/seller/mealOrder/list?pageNumber=${index}&size=${size}">${index}</a>
                    </li>
                            </#if>
                        </#list>

                    <#elseif totalPage gte 11>
                    <#--当前页小于4-->
                        <#if currentPage lt 5>
                            <#list 1..5 as index>
                                <#if currentPage==index>
                             <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                             <li>
                                 <a href="/canteen/seller/mealOrder/list?pageNumber=${index}&size=${size}">${index}</a>
                             </li>
                                </#if>
                            </#list>
                    <li class="disabled"><a href="#">...</a></li>
                    <li><a href="/canteen/seller/mealOrder/list?pageNumber=${totalPage}&size=${size}">${totalPage}</a>
                    </li>
                        </#if>
                    <#--当前页大于4小于等于total-4-->
                        <#if (currentPage gte 5)&&(currentPage lt totalPage-3)>
                    <li><a href="/canteen/seller/mealOrder/list?pageNumber=1&size=${size}">1</a></li>
                    <li class="disabled"><a href="#">...</a></li>
                            <#list currentPage-2..currentPage+2 as index>
                                <#if currentPage==index>
                             <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                              <li>
                                  <a href="/canteen/seller/mealOrder/list?pageNumber=${index}&size=${size}">${index}</a>
                              </li>
                                </#if>
                            </#list>
                    <li class="disabled"><a href="#">...</a></li>
                    <li><a href="/canteen/seller/mealOrder/list?pageNumber=${totalPage}&size=${size}">${totalPage}</a>
                    </li>
                        </#if>
                    <#--当前页大于total-4-->
                        <#if currentPage gte totalPage-3>
                    <li><a href="/canteen/seller/mealOrder/list?pageNumber=1&size=${size}">1</a></li>
                    <li class="disabled"><a href="#">...</a></li>
                            <#list totalPage-4..totalPage as index>
                                <#if currentPage==index>
                             <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                             <li>
                                 <a href="/canteen/seller/mealOrder/list?pageNumber=${index}&size=${size}">${index}</a>
                             </li>
                                </#if>
                            </#list>

                        </#if>
                    </#if>
                    <#if currentPage gte mealDTOPage.getTotalPages()>
                    <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                    <li>
                        <a href="/canteen/seller/mealOrder/list?pageNumber=${currentPage+1}&size=${size}">下一页</a>
                    </li>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
</div>
</div>

<script>
    var schoolList =${schoolListJson};
    var cls = '${cls!''}'
    $(function () {
        var index = $("#schSelect").find("option:selected").attr("id");
        var clsList = schoolList[index].clsInfoList;
        $("#clsSelect").empty();
        $("#clsSelect").append("<option value=''>--请选择--</option>")
        for (var i = 0; i < clsList.length; i++) {
            var clsName = clsList[i].clsName;
            if (clsName == cls) {
                $("#clsSelect").append(
                        "<option value=" + clsName + " selected='selected'>"
                        + clsName + "</option>");
            } else {
                $("#clsSelect").append(
                        "<option value=" + clsName + ">"
                        + clsName + "</option>");
            }
        }

    });


    function findCls() {
        $("#clsSelect").empty();
        var index = $("#schSelect").find("option:selected").attr("id");
        if (!index) {
            $("#clsSelect").append("<option value=''>--请选择--</option>")
            return
        }
        var clsList = schoolList[index].clsInfoList;
        $("#clsSelect").append("<option value=''>--请选择--</option>")
        for (var i = 0; i < clsList.length; i++) {
            var clsName = clsList[i].clsName;

            $("#clsSelect").append(
                    "<option value=" + clsName + ">"
                    + clsName + "</option>");
        }
    }

    Date.prototype.format = function (format) {
        var args = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
            "S": this.getMilliseconds()
        };
        if (/(y+)/.test(format))
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var i in args) {
            var n = args[i];
            if (new RegExp("(" + i + ")").test(format))
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
        }
        return format;
    };


</script>

</body>
</html>
