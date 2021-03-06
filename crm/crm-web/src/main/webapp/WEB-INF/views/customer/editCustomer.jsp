<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-修改客户</title>
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
    <%@include file="../include/sider.jsp"%>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">修改客户</h3>
                    <div class="box-tools pull-right">
                        <a href="/customer/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i>返回列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post">

                        <input class="hidden" name="userId" value="${customer.userId}">
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="text" name="custName" value="${customer.custName}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>性别</label>
                            <div>
                                <label class="radio-inline">
                                    <input type="radio" name="sex" value="男" ${customer.sex == '男'? 'checked' :''}> 先生
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="sex" value="女" ${customer.sex == '女'? 'checked' :''}> 女士
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>职位</label>
                            <input type="text" name="jobTitle" value="${customer.jobTitle}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>联系方式</label>
                            <input type="text" name="mobile" value="${customer.mobile}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>地址</label>
                            <input type="text" name="address" value="${customer.address}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>所属行业</label>
                            <select class="form-control" name="trade">
                                <option value=""></option>
                                <c:forEach items="${tradeList}" var="trade">
                                    <option value="${trade}" ${customer.trade == trade ? 'selected':''}>${trade}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>客户来源</label>
                            <select  class="form-control" name="source">
                                <option value=""></option>
                                <c:forEach items="${sourceList}" var="source">
                                    <option value="${source}" ${customer.source == source ? 'selected':''}>${source}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>级别</label>
                            <select name="level" class="form-control">
                                <option ${customer.level == '★'? 'selected' : ''} value="★">★</option>
                                <option ${customer.level == '★★'? 'selected' : ''} value="★★">★★</option>
                                <option ${customer.level == '★★★'? 'selected' : ''} value="★★★">★★★</option>
                                <option ${customer.level == '★★★★'? 'selected' : ''} value="★★★★">★★★★</option>
                                <option ${customer.level == '★★★★★'? 'selected' : ''} value="★★★★★">★★★★★</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>备注</label>
                            <input type="text" name="mark" value="${customer.mark}" class="form-control">
                        </div>
                        <div class="box-footer">
                            <button class="btn btn-primary">保存</button>
                        </div>
                    </form>
                </div>

                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
</body>
</html>
