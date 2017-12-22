<%--
  Created by IntelliJ IDEA.
  User: liushuai
  Date: 2017/12/21
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛 TMS-景区信息</title>
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
        <jsp:param name="menu" value="base_scenic"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">景区信息</h3>
                    <div class="box-tools pull-right">
                        <a href="/scenic/new" class="btn btn-success btn-sm"><i class="fa fa-plus"></i>新增景区</a>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table table-hover">
                        <tbody>
                        <c:if test="${not empty message}">
                            <p class="">${message}</p>
                        </c:if>
                        <tr>
                            <th>景区名称</th>
                            <th>星级</th>
                            <th>地址</th>
                            <th>负责人</th>
                            <th>联系方式</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        <c:if test="${empty scenicPageInfo.list}">
                            <tr>
                                <td colspan="6">景区信息为空，可以 <a href="/scenic/new">新增景区信息</a></td>
                            </tr>
                        </c:if>
                        <c:forEach items="${scenicPageInfo.list}" var="scenic">
                            <tr class="detail" rel="${scenic.id}">
                                <td>${scenic.scenicName}</td>
                                <td>${scenic.scenicLevel}</td>
                                <td>${scenic.scenicAddress}</td>
                                <td>${scenic.scenicManager}</td>
                                <td><i class="fa fa-phone"></i> ${scenic.scenicTel} <br></td>
                                <td><fmt:formatDate value="${scenic.createTime}"/></td>
                                <td><a href="/scenic/edit?id=${scenic.id}">修改</a></td>
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
            window.location.href = "/scenic/detail?id="+id;
        })
    });


</script>
</body>
</html>

