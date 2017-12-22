<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛 TMS-用户管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@include file="../include/css.jsp"%>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <%@include file="../include/header.jsp"%>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="list"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">用户管理</h3>
                    <div class="box-tools pull-right">
                        <a href="/account/new" class="btn btn-success btn-sm"><i class="fa fa-plus"></i>新增用户</a>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tbody>
                        <tr>
                            <th>用户名</th>
                            <th>创建时间</th>
                            <th>状态</th>
                            <th>联系方式</th>
                            <th>操作</th>
                        </tr>
                        <c:if test="${empty accountPageInfo.list}">
                            <tr>
                                <td colspan="6">你还没有客户，可以 <a href="/account/new">新增客户</a></td>
                            </tr>
                        </c:if>
                        <c:forEach items="${accountPageInfo.list}" var="account">
                            <tr class="detail" rel="${account.id}">
                                <td>${account.accountName}</td>
                                <td><fmt:formatDate value="${account.createTime}"/></td>
                                <td class="star">${account.accountState}</td>
                                <td><i class="fa fa-phone"></i> ${account.accountMobile} <br></td>
                                <td><a href="/account/delete/${account.id}">删除</a> <a href="/account/update/${account.id}">修改</a></td>
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
    <%@include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script>

    $(function () {

        $(".detail").click(function () {

            var id = $(this).attr("rel");
            window.location.href = "javaScript:;";
        })
    });


</script>
</body>
</html>
