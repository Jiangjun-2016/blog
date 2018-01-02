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
    <link href="<%=basePath%>/js/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>/css/index.css">
    <link rel="stylesheet" href="<%=basePath%>/css/login/login.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
    <link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="<%=basePath%>/js/common/html5shiv.js"></script>
    <![endif]-->
    <script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="#">灵均</a>
                    </div>
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">首页</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                Java <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="#">JDK</a></li>
                                <li class="divider"></li>
                                <li><a href="#">Spring</a></li>
                                <li><a href="#">Mybatis</a></li>
                                <li><a href="#">Netty</a></li>
                                <li><a href="#">Ignite</a></li>
                                <li class="divider"></li>
                                <li><a href="#">Utils:SQL</a></li>
                                <li><a href="#">Utils:Thread</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                BigData <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Zookeeper</a></li>
                                <li><a href="#">Hadoop</a></li>
                                <li><a href="#">HBase</a></li>
                                <li><a href="#">Hive</a></li>
                                <li><a href="#">Spark</a></li>
                                <li><a href="#">Kafka</a></li>
                                <li class="divider"></li>
                                <li><a href="#">Elasticsearch</a></li>
                                <li><a href="#">Solr</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                财务 <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="#">中级会计</a></li>
                                <li><a href="#">高级会计</a></li>
                                <li><a href="#">注册会计</a></li>
                                <li class="divider"></li>
                                <li><a href="#">总结</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                京天利工具<b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="#">步骤1</a></li>
                                <li><a href="#">步骤2</a></li>
                                <li><a href="#">步骤3</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                二宝仔<b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="#">照片</a></li>
                                <li><a href="#">视频</a></li>
                            </ul>
                        </li>
                        <li><a href="#">GitHub源码</a></li>
                        <li><a href="#">本站简介</a></li>
                    </ul>
                    <form action="##" class="navbar-form navbar-left">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Search"/>
                        </div>
                        <button type="submit" class="btn btn-default">搜索</button>
                    </form>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#"><span class="glyphicon glyphicon-user"></span> 注册</a></li>
                        <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> 登录</a></li>
                    </ul>
                </div>
            </nav>
        </div>
    </div>
    <div class="row clearfix" style="margin-top: 50px">
        <div class="col-md-12 column">
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>敬请期待!</h4>
                <strong>Hello!</strong> 彻底完工遥遥无期，哈哈哈。
            </div>
        </div>
    </div>
</div>
</body>
</html>