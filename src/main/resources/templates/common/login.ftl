<html>
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.0.0/jquery.min.js"></script>
    <script type="text/javascript" src="/canteen/js/jsencrypt.js"></script>
</head>
<body>
<div class="container">
    <div class="row clearfix" >
        <div class="col-md-4 column" style=" position: absolute;
            top: 50%;
            left: 50%;
            -webkit-transform: translate(-50%, -50%);
            -moz-transform: translate(-50%, -50%);
            -ms-transform: translate(-50%, -50%);
            -o-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);   ">
            <div class="col-md-12 column">
                <h3 class="text-center text-info">
                    请登录
                </h3>
            </div>
            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-8">
                        <input class="form-control" id="username" type="text" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-8">
                        <input class="form-control" id="password" type="password" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="button" onclick="login()" class="btn btn-default">登录</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


</body>
<script>
    var publicKey = '${publicKey}';
    function login() {
        var pstring = $.trim($("#password").val());
        var username = $.trim($("#username").val());
        var encrypt = new JSEncrypt();
        if (publicKey != null) {
            encrypt.setPublicKey(publicKey);
            var password = encrypt.encrypt(pstring);
            //提交之前，检查是否已经加密。假设用户的密码不超过20位，加密后的密码不小于20位
            if (password.length < 20) {
                //加密失败提示
                alert("登录失败，请稍后重试...");
            } else {
                console.log("ajax")
                $.ajax({
                    url: "/canteen/seller/login",
                    type: "post",
                    data: {"username": username, "password": password},
                    dataType: "json",
                    error : function() {
                        alert('登录失败 ');
                    },
                    success : function(data) {
                       if(data.code==0)  window.location.href = '/canteen/';
                       else alert(data.msg)
                    }

                })

            }
        }
    }

</script>
</html>