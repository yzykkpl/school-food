<html>
<head>
    <meta charset="utf-8">
    <title>学校详情</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
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
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>学校Id</th>
                            <th>名称</th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${schoolInfo.schoolId}</td>
                            <td>${schoolInfo.schoolName}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <#--班级列表-->
                <div class="col-md-6 column">
                    <a  class="btn btn-default" style="margin-bottom: 10px" href="/canteen/seller/school/clsIndex?schoolId=${schoolInfo.schoolId}">添加班级</a>
                    <table class="table table-bordered" style="word-break:break-all; word-wrap:break-all;">
                        <thead>
                        <tr>
                            <th >序号</th>
                            <th >名称</th>
                            <th >操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list clsInfoList as clsInfo>
                        <tr>
                            <td>${clsInfo_index+1}</td>
                            <td>${clsInfo.clsName}</td>
                            <td><a href="/canteen/seller/school/clsIndex?clsId=${clsInfo.clsId}&schoolId=${schoolInfo.schoolId}">修改</a></td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
