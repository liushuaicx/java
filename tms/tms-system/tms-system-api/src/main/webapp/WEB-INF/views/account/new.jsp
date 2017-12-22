<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛 TMS-新增用户</title>
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
                    <h3 class="box-title">新增客户</h3>
                    <div class="box-tools pull-right">
                        <a href="/account/list" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i>返回列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post" id="addForm">
                        <div class="form-group">
                            <label>用户名</label>
                            <input type="text" name="accountName" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>联系方式</label>
                            <input type="text" name="accountMobile" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>密码</label>
                            <input type="password" name="accountPassword" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>拥有角色</label>
                            <select class="form-control" name="roleId">
                                <option value=""></option>
                                <c:forEach items="${roleList}" var="role">
                                    <option value="${role.id}">${role.roleName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                    <div class="box-footer">
                        <button id="saveBtn" class="btn btn-primary">保存</button>
                    </div>
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
<script src="/static/plugins/validate/jquery.validate.min.js"></script>
<script>
    $(function () {

        $("#saveBtn").click(function () {
            $("#addForm").submit()
        });
        $("#addForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                accountName:{
                    required:true
                },
                accountMobile:{
                    required:true
                },
                accountPassword:{
                    required:true
                }
            },
            messages:{
                accountName:{
                    required:"请输入用户名"
                },
                accountMobile:{
                    required:"请输入联系方式"
                },
                accountPassword:{
                    required:"请输入密码"
                }
            }
        });

    });
</script>
</body>
</html>
