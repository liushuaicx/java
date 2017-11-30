<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-我的销售机会</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <!-- 顶部导航栏部分 -->
    <%@include file="../include/header.jsp"%>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="sale_my"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">我的销售机会</h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool">
                            <i class="fa fa-plus"></i> <a href="/sale/new">添加机会</a>
                        </button>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>机会名称</th>
                                <th>关联客户</th>
                                <th>机会价值</th>
                                <th>当前进度</th>
                                <th>最后跟进时间</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${saleChanceList}" var="sale">
                                <tr  class="sales_row" rel="${sale.id}">
                                    <td>${sale.name}</td>
                                    <td>${sale.customer.custName}</td>
                                    <td><fmt:formatNumber value="${sale.worth}"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${sale.progress == '成交'}">
                                                <span class="label label-success">${sale.progress}</span>
                                            </c:when>
                                            <c:when test="${sale.progress == '暂时搁置'}">
                                                <span class="label label-danger">${sale.progress}</span>
                                            </c:when>
                                            <c:otherwise>
                                                ${sale.progress}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><fmt:formatDate value="${sale.lastTime}" pattern="YY-MM-dd HH:mm"/></td>
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
        $(".sales_row").click(function () {
            var id = $(this).attr("rel");
            window.location.href = "/sale/detail/"+id;
        });
    })
</script>
</body>
</html>
