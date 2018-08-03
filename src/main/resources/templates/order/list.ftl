<html>
<head>
    <meta charset="utf-8">
    <title>订单列表</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/jquery-impromptu/6.2.3/jquery-impromptu.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.0.0/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery-impromptu/6.2.3/jquery-impromptu.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="/canteen/js/selectUtil.js"></script>


    <link href="/canteen/css/style.css" rel="stylesheet">
</head>
<body>
    <#assign totalPage = orderDTOPage.getTotalPages()>
    <#assign total = orderDTOPage.getTotalElements()>
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
                                <#--<li><a href="/canteen/seller/order/dateList?filter=-1">全部</a></li>-->
                                <#--<li><a href="/canteen/seller/order/dateList?filter=0">新订单</a></li>-->
                                <#--<li><a href="/canteen/seller/order/dateList?filter=1">已完结</a></li>-->
                                <#--<li><a href="/canteen/seller/order/dateList?filter=2">已取消</a></li>-->
                            <#--</ul>-->
                        <#--</li>-->
                        <#--</div>-->
                        <div class="col-xs-8">

                            <form role="form" class="form-inline" method="get" action="/canteen/seller/order/dateList">
                                <label style="color: blue;font-size: 15px;margin-right: 3px">按预定时间段筛选:</label>
                                <div class="form-group" >
                                    <label for="schSelect" >学校</label>
                                    <select name="school" id="schSelect" value='${school!''}' class="form-control" onchange="findCls()"/>
                                        <option value="">--请选择--</option>
                                        <#list schoolList as sch>
                                            <#assign index=sch_index/>
                                            <option value=${sch.schoolName} id=${index} <#if (((school)!'') == sch.schoolName)>selected="selected"</#if> >${sch.schoolName}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="clsSelect" >班级</label>
                                    <select name="cls" id="clsSelect" value='${cls!''}'  class="form-control"/>
                                        <option value="">--请选择--</option>
                                    </select>
                                </div>
                                <div class="form-group" >
                                    <label for="f1" >开始时间</label>
                                    <input name="start" id="f1" type="date" value=${start!'2018-01-01'} class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <label for="f2">截止时间</label>
                                    <input name="end" id="f2" type="date" value=${end!'2018-01-31'} class="form-control"/>
                                </div>
                                <button type="submit" class="btn btn-default">查询</button>
                            </form>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <button onclick="getExcel()">按时间段导出Excel</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-12 column">

                    <table class="table table-bordered table-condensed" style="word-break:break-all; word-wrap:break-all;">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>订单ID</th>
                            <th>商品</th>
                            <th>数量</th>
                            <th>金额</th>
                            <th>预定日期</th>
                            <th class="col-md-2">备注</th>
                            <th>姓名</th>
                            <th>学校</th>
                            <th>班级</th>
                            <th>学号</th>
                            <th>手机号</th>
                            <#--<th>支付状态</th>-->
                            <th>创建时间</th>
                        </tr>
                        </thead>
                        <tbody>
                    <#list orderDTOPage.content as orderDTO>
                    <tr>
                        <td>${orderDTO_index+1}</td>
                        <td>${orderDTO.orderId}</td>
                        <td>${orderDTO.snapName}</td>
                        <td>${orderDTO.counts}</td>
                        <td>${orderDTO.orderAmount}</td>
                        <td>${orderDTO.date?string("yyyy-MM-dd")}</td>
                        <td >${orderDTO.comment!""}</td>
                        <td>${orderDTO.buyerName}</td>
                        <td>${orderDTO.buyerSchool}</td>
                        <td>${orderDTO.buyerCls}</td>
                        <td>${orderDTO.stdNum}</td>
                        <td>${orderDTO.buyerPhone}</td>
                        <#--<td>${orderDTO.getPayStatusEnum().getMessage()}</td>-->
                        <td>${orderDTO.createTime}</td>
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
                          </tr>
                        </#list>
                    </#if>
                        </tbody>
                    </table>
                </div>

            </div>
        <#--分页-->
            <div class="col-md-12 column">
                <div class="col-xs-6">订单总数:${total}</div>

                <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                    <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                    <li>
                        <a href="/canteen/seller/order/dateList?page=${currentPage - 1}&size=${size}&school=${school!''}&cls=${cls!''}&start=${start!''}&end=${end!''}">上一页</a>
                    </li>
                    </#if>
                    <#if totalPage lte 10>
                        <#list 1..totalPage as index>
                            <#if currentPage==index>
                    <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                    <li><a href="/canteen/seller/order/dateList?page=${index}&size=${size}&school=${school!''}&cls=${cls!''}&start=${start!''}&end=${end!''}">${index}</a>
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
                                 <a href="/canteen/seller/order/dateList?page=${index}&size=${size}&school=${school!''}&cls=${cls!''}&start=${start!''}&end=${end!''}</a>
                             </li>
                                </#if>
                            </#list>
                    <li class="disabled"><a href="#">...</a></li>
                    <li><a href="/canteen/seller/order/dateList?page=${totalPage}&size=${size}&school=${school!''}&cls=${cls!''}&start=${start!''}&end=${end!''}">${totalPage}</a>
                    </li>
                        </#if>
                    <#--当前页大于4小于等于total-4-->
                        <#if (currentPage gte 5)&&(currentPage lt totalPage-3)>
                    <li><a href="/canteen/seller/order/dateList?page=1&size=${size}&school=${school!''}&cls=${cls!''}&start=${start!''}&end=${end!''}">1</a></li>
                    <li class="disabled"><a href="#">...</a></li>
                            <#list currentPage-2..currentPage+2 as index>
                                <#if currentPage==index>
                             <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                              <li>
                                  <a href="/canteen/seller/order/dateList?page=${index}&size=${size}&school=${school!''}&cls=${cls!''}&start=${start!''}&end=${end!''}">${index}</a>
                              </li>
                                </#if>
                            </#list>
                    <li class="disabled"><a href="#">...</a></li>
                    <li><a href="/canteen/seller/order/dateList?page=${totalPage}&size=${size}&school=${school!''}&cls=${cls!''}&start=${start!''}&end=${end!''}">${totalPage}</a>
                    </li>
                        </#if>
                    <#--当前页大于total-4-->
                        <#if currentPage gte totalPage-3>
                    <li><a href="/canteen/seller/order/dateList?page=1&size=${size}&school=${school!''}&cls=${cls!''}&start=${start!''}&end=${end!''}">1</a></li>
                    <li class="disabled"><a href="#">...</a></li>
                            <#list totalPage-4..totalPage as index>
                                <#if currentPage==index>
                             <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                             <li>
                                 <a href="/canteen/seller/order/dateList?page=${index}&size=${size}&school=${school!''}&cls=${cls!''}&start=${start!''}&end=${end!''}">${index}</a>
                             </li>
                                </#if>
                            </#list>

                        </#if>
                    </#if>
                    <#if currentPage gte orderDTOPage.getTotalPages()>
                    <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                    <li>
                        <a href="/canteen/seller/order/dateList?page=${currentPage + 1}&size=${size}&school=${school!''}&cls=${cls!''}&start=${start!''}&end=${end!''}">下一页</a>
                    </li>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
</div>
</div>

<#--弹窗-->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒
                </h4>
            </div>
            <div class="modal-body">
                你有新的订单
            </div>
            <div class="modal-footer">
                <button onclick="javascript:document.getElementById('notice').pause()" type="button"
                        class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
            </div>
        </div>
    </div>
</div>
<#--取消提示-->

<script>
    var schoolList=${schoolListJson};
    console.log(schoolList)
    var cls='${cls!''}'
    $(function(){
        var index = $("#schSelect").find("option:selected").attr("id");
        var clsList=schoolList[index].clsInfoList;
        $("#clsSelect").empty();
        $("#clsSelect").append("<option value=''>--请选择--</option>")
        for ( var i = 0; i < clsList.length; i++) {
            var clsName = clsList[i].clsName;
            if(clsName==cls){
                $("#clsSelect").append(
                        "<option value=" + clsName + " selected='selected'>"
                        + clsName + "</option>");
            }else {
                $("#clsSelect").append(
                        "<option value=" + clsName + ">"
                        + clsName + "</option>");
            }
        }

    });
    function showTip(orderId) {
        console.log(orderId)
        $.prompt("取消订单可能造成退款，您确认取消吗？", {
            title: "警告",
            buttons: {"是的，取消": true, "不取消": false},
            submit: function (e, v, m, f) {
                if (v) location.href = '/canteen/seller/order/cancel?orderId=' + orderId;
            }
        });
    }

    function findCls(){
        $("#clsSelect").empty();

        var index = $("#schSelect").find("option:selected").attr("id");
        if (!index) {
            $("#clsSelect").append("<option value=''>--请选择--</option>")
            return
        }
        var clsList=schoolList[index].clsInfoList;

        $("#clsSelect").append("<option value=''>--请选择--</option>")
        for ( var i = 0; i < clsList.length; i++) {
            var clsName = clsList[i].clsName;

            $("#clsSelect").append(
                    "<option value="+clsName+">"
                    + clsName + "</option>");
        }
    }
    function getExcel() {
        var start = $('#f1').val()
        var end = $('#f2').val()


        // console.log('school',school)
        // console.log('cls',cls)
        // console.log('start',start)
        // console.log('end',end)
        // console.log('mealId',mealId)

        if(!start){
            alert("请选择开始日期")
            return;
        }

        if(!end){
            alert("请选择结束日期")
            return;
        }
        window.open("/canteen/seller/order/getExcel?start="+start+"&end="+end);

    }


</script>

</body>
</html>
