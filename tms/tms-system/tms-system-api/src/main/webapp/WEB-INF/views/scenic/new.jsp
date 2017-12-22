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
    <title>凯盛 TMS-新增景区</title>
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
                    <h3 class="box-title">新增景区</h3>
                    <div class="box-tools pull-right">
                        <a href="/scenic/" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i>返回列表</a>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post" id="addForm" enctype="multipart/form-data">
                        <div class="form-group">
                            <label>景区名称</label>
                            <input type="text" name="scenicName" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>星级</label>
                            <select class="form-control" name="scenicLevel">
                                <option value=""></option>
                                <option value="AAA">AAA</option>
                                <option value="AAAA">AAAA</option>
                                <option value="AAAAA">AAAAA</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>地址</label>
                            <input type="text" name="scenicAddress" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>上传营业执照</label>
                            <input type="file" name="file" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>负责人</label>
                            <input type="text" name="scenicManager" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>联系方式</label>
                            <input type="text" name="scenicTel" class="form-control">
                        </div>
                        <div class="form-group">
                            <textarea name="scenicIntro" class="from-control" id="editor"  placeholder="正文从这里开始..."></textarea>
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
<script src="/static/dist/js/app.min.js"></script>
<script src="/static/plugins/editer/scripts/module.min.js"></script>
<script src="/static/plugins/editer/scripts/hotkeys.min.js"></script>
<script src="/static/plugins/editer/scripts/uploader.min.js"></script>
<script src="/static/plugins/editer/scripts/simditor.min.js"></script>
<script src="/static/plugins/validate/jquery.validate.min.js"></script>
<script>
    $(function () {

        var editor = new Simditor({
            textarea: $('#editor'),
            upload: {
                url:'/file/upload',
                fileKey :'upload'
            }
        });

        $("#saveBtn").click(function () {
            $("#addForm").submit()
        });
        $("#addForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                scenicName:{
                    required:true
                },
                scenicLevel:{
                    required:true
                },
                scenicAddress:{
                    required:true
                },
                file:{
                    required:true
                },
                scenicManager:{
                    required:true
                },
                scenicTel:{
                    required:true
                }

            },
            messages:{
                scenicName:{
                    required:"请输入景区名"
                },
                scenicLevel:{
                    required:"请选择星级"
                },
                scenicAddress:{
                    required:"请输入地址"
                },
                file:{
                    required:"请选择文件"
                },
                scenicManager:{
                    required:"请输入负责人"
                },
                scenicTel:{
                    required:"请输入联系电话"
                }
            }
        });

    });
</script>
</body>
</html>

