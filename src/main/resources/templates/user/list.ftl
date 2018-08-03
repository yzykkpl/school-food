<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <title>用户列表</title>
    <link href="/canteen/css/b.page.bootstrap3.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/jquery-impromptu/6.2.3/jquery-impromptu.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.0.0/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery-impromptu/6.2.3/jquery-impromptu.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/canteen/js/userLoad.js"></script>
    <script type="text/javascript" src="/canteen/js/b.page.js"></script>


    <link href="/canteen/css/style.css" rel="stylesheet">
</head>
<body>
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
                        <div class="col-xs-12">

                            <form role="form" class="form-inline" method="get" action="#">
                                <label style="color: blue;font-size: 15px;margin-right: 3px">条件筛选:</label>
                                <div class="form-group">
                                    <label for="schSelect">学校:</label>
                                    <select name="school" id="schSelect" value=''  class="form-control"
                                            onchange="findCls()"/>
                                    <option value="">--请选择--</option>
                                        <#list schoolList as sch>
                                            <#assign index=sch_index/>
                                            <option value=${sch.schoolId} id=${index}>${sch.schoolName}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="clsSelect">班级:</label>
                                    <select name="cls" id="clsSelect" value='' class="form-control"/>
                                    <option value="">--请选择--</option>
                                    </select>
                                </div>
                                <div class="form-group" >
                                    <label for="name">姓名:</label>
                                    <input name="name" id="name" type="text" class="form-control" "/>
                                </div>
                                <div class="form-group">
                                    <label for="status">身份:</label>
                                    <label style="font-size:70%;color: red;">(选择班级则无效)</label>
                                    <select name="status" id="status" value='' class="form-control"/>
                                    <option value="">全部</option>
                                    <option value="0">学生</option>
                                    <option value="1">老师</option>
                                    </select>

                                </div>
                                <button type="button" onclick="select()" class="btn btn-default">查询</button>
                            </form>

                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-10">
                            <button id="checkAll">全选</button>
                            <button id="nothing">全不选</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row clearfix">
                <div id="page1" class="col-md-12 column">
                    <table id="table2" class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th><input type="checkbox" id="All"/></th>
                            <th>序号</th>
                            <th>姓名</th>
                            <th>学号</th>
                            <th>学校</th>
                            <th>班级</th>
                            <th>身份</th>
                            <th>手机</th>
                        </tr>
                        </thead>

                        <tbody id="tbody">

                        </tbody>
                    </table>


                </div>
            </div>



        <#--分页-->
            <div>
                <button id="checkAll" onclick="del()">删除</button>
            </div>
        </div>
    </div>
</div>
</div>

<script>
    var schoolList =${schoolListJson};
    <#--var cls = '${cls!''}'-->
    <#--$(function () {-->
        <#--var index = $("#schSelect").find("option:selected").attr("id");-->
        <#--var clsList = schoolList[index].clsInfoList;-->
        <#--$("#clsSelect").empty();-->
        <#--$("#clsSelect").append("<option value=''>--请选择--</option>")-->
        <#--for (var i = 0; i < clsList.length; i++) {-->
            <#--var clsName = clsList[i].clsName;-->
            <#--if (clsName == cls) {-->
                <#--$("#clsSelect").append(-->
                        <#--"<option value=" + clsName + " selected='selected'>"-->
                        <#--+ clsName + "</option>");-->
            <#--} else {-->
                <#--$("#clsSelect").append(-->
                        <#--"<option value=" + clsName + ">"-->
                        <#--+ clsName + "</option>");-->
            <#--}-->
        <#--}-->

    <#--});-->


    function findCls() {
        $("#clsSelect").empty();
        var index = $("#schSelect").find("option:selected").attr("id");
        if (!index) {
            $("#clsSelect").append("<option value=\"\">--请选择--</option>")
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

    $("#All").click(function(){
        if("this.checked"){
            $("#table2 :checkbox").prop("checked", true);
        }else{
            $("#table2 :checkbox").prop("checked", false);
        }
    });

    <!--选择全部-->
    $("#checkAll").click(function(){
        $("#table2 :checkbox").prop("checked", true);
    });

    <!--全不选-->
    $("#nothing").click(function(){
        $("#table2 :checkbox").prop("checked", false);
    });

    $(select())


</script>

</body>
</html>
