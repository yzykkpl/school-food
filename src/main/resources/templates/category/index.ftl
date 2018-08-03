<html>
<head>
    <meta charset="utf-8">
    <title>商品列表</title>
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
                    <form role="form" method="post" action="/canteen/seller/category/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="categoryName" class="form-control"  type="text" value="${(category.categoryName)!""}" />
                        </div>
                        <div class="form-group">
                            <label>type</label>
                            <input name="categoryType" class="form-control"  type="number" value="${(category.categoryType)!""}" />
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <input name="categoryIcon" class="form-control"  type="text" value="${(category.categoryIcon)!""}" />
                        </div>

                        <input  hidden type="number" name="categoryId" value="${(category.categoryId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
