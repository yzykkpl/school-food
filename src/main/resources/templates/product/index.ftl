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
                    <form role="form" method="post" action="/canteen/seller/product/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="productName" class="form-control"  type="text" value="${(productInfo.productName)!""}" />
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input name="productPrice" class="form-control"  type="number" value="${(productInfo.productPrice)!""}" />
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="productDescription" class="form-control"  type="text" value="${(productInfo.productDescription)!""}" />
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img src="${(productInfo.productIcon)!""}" height="200" width="200">
                            <input name="productIcon" class="form-control"  type="text" value="${(productInfo.productIcon)!''}" />
                        </div>
                        <div class="form-group">
                            <label>详情图片</label>
                            <input name="detailImage" class="form-control"  type="text" value="${(productInfo.detailImage)!''}" />
                        </div>
                        <div class="form-group">
                            <label>对应学校</label>
                            <select name="schoolId" class="form-control">
                                <option value="-1">全部</option>
                                <#list schoolInfoList as schoolInfo>
                                    <option value="${schoolInfo.schoolId}"
                                            <#if (productInfo.schoolId)?? && productInfo.schoolId == schoolInfo.schoolId>
                                            selected
                                            </#if>>
                                        ${schoolInfo.schoolName}
                                    </option>
                                </#list>
                            </select>

                        </div>
                        <input  hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <input  hidden type="number" name="categoryType" value="${(productInfo.categoryType)!2}">
                       <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
