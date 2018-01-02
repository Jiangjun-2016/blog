<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>来，聊聊</title>
    <link rel="icon" href="<%=basePath%>/imgs/jun.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="<%=basePath%>/imgs/jun.ico"/>
    <link rel="stylesheet" href="<%=basePath%>/css/login/login.css">
    <link href="<%=basePath%>/js/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
    <link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="<%=basePath%>/js/common/html5shiv.js"></script>
    <![endif]-->
    <script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<body>
<div class="container">
    <div class="row">
        <div class="form-horizontal col-md-offset-3" role="form"
             style="width: 400px;margin: 120px auto;" id="login_form">
            <h3 class="form-title">来，聊聊</h3>
            <div class="col-md-9">
                <div class="form-group">
                    <i class="fa fa-user fa-lg"></i>
                    <input class="form-control required" type="text" placeholder="Username" id="username"
                           name="username" autofocus="autofocus" maxlength="20"/>
                </div>
                <div class="form-group">
                    <i class="fa fa-lock fa-lg"></i>
                    <input class="form-control required" type="password" placeholder="Password" id="password"
                           name="password" maxlength="8"/>
                </div>
                <div class="form-group">
                    <label class="checkbox">
                        <input type="checkbox" checked="true" name="remember" value="1"/>记住我
                    </label>
                </div>
                <div class="form-group col-md-offset-9">
                    <button type="submit" class="btn btn-primary btn-block" name="submit">登录</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</body>
</html>