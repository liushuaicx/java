<%--
  Created by IntelliJ IDEA.
  User: liushuai
  Date: 2017/12/20
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛 TMS-修改用户</title>
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
                        <a href="/account/list" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i>返回列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post" id="addForm">
                        <div class="form-group">
                            <label>用户名</label>
                            <input type="hidden" name="id" value="${account.id}">
                            <input type="hidden" value="${account.createTime}" name="createTime">
                            <input type="text" value="${account.accountName}" name="accountName" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>联系方式</label>
                            <input type="text" value="${account.accountMobile}" name="accountMobile" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>状态</label>
                            <div>
                                <label class="radio-inline">
                                    <input type="radio" name="accountState" value="正常" ${account.accountState == '正常'? 'checked' :''}> 正常
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="accountState" value="冻结" ${account.accountState == '冻结'? 'checked' :''} > 冻结
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>密码</label>
                            <input type="password"value="${account.accountPassword}"  name="accountPassword" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>拥有角色</label>
                            <div>
                                <c:forEach items="${roleList}" var="role">
                                    <label class="radio-inline">
                                        <input type="checkbox" name="roleId" value="${role.id}">${role.roleName}
                                    </label>
                                </c:forEach>
                            </div>
                        </div>
                    </form>
                    <div class="box-footer">
                        <button id="saveBtn" class="btn btn-primary">修改</button>
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

