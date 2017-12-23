<%--
  Created by IntelliJ IDEA.
  User: liushuai
  Date: 2017/12/21
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛 TMS-年票办理</title>
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
        <jsp:param name="menu" value="handle"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">年票办理</h3>
                </div>
                <c:if test="${not empty message}">
                    <p class="login-box-msg">${message}</p>
                </c:if>
                <div class="box-body">
                    <form method="post" id="addForm" enctype="multipart/form-data">
                        <div class="form-group">
                            <label>卡号</label>
                            <input type="text" name="ticketNum" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>开卡类型</label>
                            <div>
                                <label class="radio-inline">
                                    <input type="radio" name="ticketOrderType" value="半票" checked> 半票
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="ticketOrderType" value="全票"> 全票
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>姓名</label>
                            <input type="text" name="customerName" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>性别</label>
                            <div>
                                <label class="radio-inline">
                                    <input type="radio" name="customerGender" value="先生" checked> 先生
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="customerGender" value="女士"> 女士
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>身份证号</label>
                            <input type="text" name="customerIdCard" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>联系电话</label>
                            <input type="text" name="customerTel" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>家庭住址</label>
                            <input type="text" name="cutomerAddress" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>上传身份证正面</label>
                            <input type="file" name="file" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>上传身份证反面</label>
                            <input type="file" name="file1" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>一寸照片</label>
                            <input type="file" name="file2" class="form-control">
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
                customerName:{
                    required:true
                },
                customerGender:{
                    required:true
                },
                customerIdCard:{
                    required:true,
                    rangelength:[18,18]
                },
                customerTel:{
                    required:true
                },
                cutomerAddress:{
                    required:true
                },
                file:{
                    required:true
                },
                file1:{
                    required:true
                },
                file2:{
                    required:true
                }

            },
            messages:{
                customerName:{
                    required:"请输入姓名"
                },
                customerGender:{
                    required:"请输入性别"
                },
                customerIdCard:{
                    required:"请输入身份证号",
                    rangelength:"请输入18位身份证号"
                },
                customerTel:{
                    required:"请输入电话"
                },
                cutomerAddress:{
                    required:"请输入地址"
                },
                file:{
                    required:"请上传身份证正面照片"
                },
                file1:{
                    required:"请上传身份证正面照片"
                },
                file2:{
                    required:"请上传一寸照片"
                }

            }
        });

    });
</script>
</body>
</html>

