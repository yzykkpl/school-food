<html>
<head>
    <meta charset="utf-8">
    <title>学校列表</title>
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

                <div class="col-md-6 column">
                    <a  class="btn btn-default" style="margin-bottom: 10px" href="/canteen/seller/school/index">添加学校</a>
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>学校Id</th>
                            <th>名称</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list schoolInfoList as schoolInfo>
                        <tr>
                            <td>${schoolInfo.schoolId}</td>
                            <td>${schoolInfo.schoolName}</td>
                            <td><a href="/canteen/seller/school/detail?schoolId=${schoolInfo.schoolId}">详情</a> </td>
                            <td><a href="/canteen/seller/school/index?schoolId=${schoolInfo.schoolId}">修改</a> </td>
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
