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
                    <form role="form" method="post" action="/canteen/seller/meal/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="mealName" class="form-control"  type="text" value="${(mealInfo.mealName)!""}" />
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input name="mealPrice" class="form-control"  type="number" value="${(mealInfo.mealPrice)!""}" />
                        </div>
                        <div class="form-group">
                            <label>天数</label>
                            <input name="days" class="form-control"  type="number" value="${(mealInfo.days)!""}" />
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="mealDescription" class="form-control"  type="text" value="${(mealInfo.mealDescription)!""}" />
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img src="${(mealInfo.mealIcon)!""}" height="200" width="200">
                            <input name="mealIcon" class="form-control"  type="text" value="${(mealInfo.mealIcon)!''}" />
                        </div>
                        <div class="form-group">
                            <label>详情图片</label>
                            <input name="detailImage" class="form-control"  type="text" value="${(mealInfo.detailImage)!''}" />
                        </div>
                        <div class="form-group">
                            <label>选择该套餐所在月份的截止日期</label>
                            <input name="mealDate" class="form-control"  type="date" value="${(mealInfo.mealDate?string('yyyy-MM-dd'))!''}" />
                        </div>
                        <div class="form-group">
                            <label>对应学校</label>
                            <select name="schoolId" class="form-control">
                                <option value="-1">全部</option>
                                <#list schoolInfoList as schoolInfo>
                                    <option value="${schoolInfo.schoolId}"
                                            <#if (mealInfo.schoolId)?? && mealInfo.schoolId == schoolInfo.schoolId>
                                            selected
                                            </#if>>
                                        ${schoolInfo.schoolName}
                                    </option>
                                </#list>
                            </select>

                        </div>
                        <input  hidden type="text" name="mealId" value="${(mealInfo.mealId)!''}">
                       <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
