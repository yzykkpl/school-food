<html>
<head>
    <meta charset="utf-8">
    <title>学校</title>
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
                    <form role="form" method="post" action="/canteen/seller/school/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="schoolName" class="form-control"  type="text" value="${(schoolInfo.schoolName)!""}" />
                        </div>
                        <input  hidden type="number" name="schoolId" value="${(schoolInfo.schoolId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
