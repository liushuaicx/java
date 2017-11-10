<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-我的客户</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@include file="include/css.jsp"%>
    <style>
        .name-avatar {
            display: inline-block;
            width: 50px;
            height: 50px;
            background-color: #ccc;
            border-radius: 50%;
            text-align: center;
            line-height: 50px;
            font-size: 24px;
            color: #FFF;
        }
        .table>tbody>tr:hover {
            cursor: pointer;
        }
        .table>tbody>tr>td {
            vertical-align: middle;
        }
        .star {
            font-size: 20px;
            color: #ff7400;
        }
    </style>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <%@include file="include/header.jsp"%>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <%@include file="include/sider.jsp"%>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">我的客户</h3>
                    <div class="box-tools pull-right">
                        <button class="btn btn-success btn-sm"><i class="fa fa-plus"></i><a href="/customer/add">新增客户</a> </button>
                        <button class="btn btn-primary btn-sm"><i class="fa fa-file-excel-o"></i> 导出Excel</button>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tbody>
                        <tr>
                            <th width="80"></th>
                            <th>姓名</th>
                            <th>职位</th>
                            <th>跟进时间</th>
                            <th>级别</th>
                            <th>联系方式</th>
                        </tr>
                        <c:forEach items="${customerPageInfo.list}" var="customer">
                            <tr>
                                <td><span class="name-avatar">${customer.custName.subSequence(0,1)}</span></td>
                                <td><a href="/customer/detail?id=${customer.id}">${customer.custName}</a></td>
                                <td>${customer.jobTitle}</td>
                                <td>${customer.lastContractTime}</td>
                                <td class="star">${customer.level}</td>
                                <td><i class="fa fa-phone"></i> ${customer.mobile} <br></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 1.0
        </div>
        <strong>Copyright &copy; 2010 -2017 <a href="http://almsaeedstudio.com">凯盛软件</a>.</strong> All rights
        reserved.
    </footer>

</div>
<!-- ./wrapper -->

<%@include file="include/js.jsp"%>
</body>
</html>
