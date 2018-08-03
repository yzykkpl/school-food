<html>
<head>
    <meta charset="utf-8">
    <title>学校列表</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.bootcss.com/jquery-impromptu/6.2.3/jquery-impromptu.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.0.0/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery-impromptu/6.2.3/jquery-impromptu.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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

                <div class="col-md-4 column">
                    <button  class="btn btn-default" style="margin-bottom: 10px" onclick="create()">添加用户</button>
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>用户名</th>
                            <th colspan="2" style="text-align: center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list sellerInfoList as sellerInfo>
                        <tr>
                            <td>${sellerInfo.username}</td>
                            <#if !(sellerInfo.username=="admin")>
                                <td><a href="/canteen/seller/user/delete?username=${sellerInfo.username}" >删除</a> </td>
                            <#else>
                                <td>-</td>
                            </#if>

                            <td><a onclick="changePwd('${sellerInfo.username}')">修改密码</a> </td>
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
<script>

    function changePwd(username) {
        var statesdemo = {
            state0: {
                title: '修改密码',
                html:'<label>用户名<input type="text" name="username" value="'+username+'" readonly="readonly"></label><br />'+
                '<label>请输入密码 <input type="password" name="password" value=""></label><br />',
                buttons: { Next: 1 },
                //focus: "input[name='fname']",
                submit:function(e,v,m,f){
                    //location.href='/canteen/seller/user/change?username='+username+'password='+f.password;

                    $.ajax({
                        url: "/canteen/seller/user/change",
                        type: "post",
                        data: {"username": f.username, "password": f.password},
                        dataType: "json",
                        error : function() {
                            alert('修改失败 ');
                        },
                        success : function(data) {
                            if(data.code==0){
                                $.prompt("修改成功，请及时通知对应用户", {
                                    title: "",
                                    buttons: { "确定": true},
                                    submit: function(e,v,m,f){
                                        if(v) location.href='/canteen/seller/user/list';
                                    }
                                });
                            }
                            else alert(data.msg)
                        }

                    })
                }
            }
        }
        $.prompt(statesdemo);
    }
    function create() {
        var statesdemo = {
            state0: {
                title: '修改密码',
                html:'<label>用户名<input type="text" name="username" value=""></label><br />'+
                '<label>请输入密码 <input type="password" name="password" value=""></label><br />',
                buttons: { Next: 1 },
                //focus: "input[name='fname']",
                submit:function(e,v,m,f){
                    //location.href='/canteen/seller/user/change?username='+username+'password='+f.password;
                    $.ajax({
                        url: "/canteen/seller/user/create",
                        type: "post",
                        data: {"username": f.username, "password": f.password},
                        dataType: "json",
                        error : function() {
                            alert('添加失败 ');
                        },
                        success : function(data) {
                            if(data.code==0){
                                $.prompt("添加成功", {
                                    title: "",
                                    buttons: { "确定": true},
                                    submit: function(e,v,m,f){
                                        if(v) location.href='/canteen/seller/user/list';
                                    }
                                });
                            }
                            else alert(data.msg)
                        }

                    })
                }
            }
        }
        $.prompt(statesdemo);
    }

</script>
</html>
