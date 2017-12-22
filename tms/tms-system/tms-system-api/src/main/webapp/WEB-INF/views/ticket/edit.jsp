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
    <title>凯盛 TMS-修改售票点</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/editer/styles/simditor.css">
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
                    <h3 class="box-title">修改售票点</h3>
                    <div class="box-tools pull-right">
                        <a href="/scenic/" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i>返回列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post" id="addForm" enctype="multipart/form-data">
                        <div class="form-group">
                            <label>售票点名称</label>
                            <input type="hidden" name="createTime" value="${ticketStore.createTime}">
                            <input type="text" value="${ticketStore.storeName}" name="storeName" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>负责人</label>
                            <input type="text" value="${ticketStore.storeManager}" name="storeManager" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>联系方式</label>
                            <input type="text" value="${ticketStore.storeTel}" name="storeTel" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>地址</label>
                            <input type="text" value="${ticketStore.storeAddress}" name="storeAddress" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>上传营业执照</label>
                            <input type="file" name="file" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>上传身份证正面</label>
                            <input type="file" name="file2" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>上传身份证反面</label>
                            <input type="file" name="file3" class="form-control">
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
                storeName:{
                    required:true
                },
                storeManager:{
                    required:true
                },
                storeTel:{
                    required:true
                },
                storeAddress:{
                    required:true
                },
                file:{
                    required:true
                },
                file2:{
                    required:true
                },
                file3:{
                    required:true
                }

            },
            messages:{
                storeName:{
                    required:"请输入售票点名称"
                },
                storeManager:{
                    required:"请输入负责人"
                },
                storeTel:{
                    required:"请输入联系方式"
                },
                storeAddress:{
                    required:"请输入售票点地址"
                },
                file:{
                    required:"请上传营业执照"
                },
                file2:{
                    required:"请上传身份证正面"
                },
                file3:{
                    required:"请上传身份证反面"
                }
            }
        });

    });
</script>
</body>
</html>

