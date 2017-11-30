<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛软件CRM-客户资料</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@include file="../include/css.jsp"%>
    <style>
        .td_title {
            font-weight: bold;
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
    <%@include file="../include/header.jsp"%>

    <!-- =============================================== -->

    <!-- 左侧菜单栏 -->
    <%@include file="../include/sider.jsp"%>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户资料</h3>
                    <div class="box-tools">
                        <a href="/customer/my" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                        <a href="/customer/edit/${customer.id}" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 编辑</a>
                        <a href="javascript:;" id="changeUser" class="btn bg-orange btn-sm"><i class="fa fa-exchange"></i> 转交他人</a>
                        <button id="public" class="btn bg-maroon btn-sm"><i class="fa fa-recycle"></i> 放入公海</button>
                        <a href="javascript:;" id="delete" rel="${customer.id}" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</a>
                    </div>
                </div>
                <div class="box-body no-padding">
                    <table class="table">
                        <tr>
                            <td class="td_title">姓名</td>
                            <td>${customer.custName}</td>
                            <td class="td_title">职位</td>
                            <td>${customer.jobTitle}</td>
                            <td class="td_title">联系电话</td>
                            <td>${customer.mobile}</td>
                        </tr>
                        <tr>
                            <td class="td_title">所属行业</td>
                            <td>${customer.trade}</td>
                            <td class="td_title">客户来源</td>
                            <td>${customer.source}</td>
                            <td class="td_title">级别</td>
                            <td>${customer.level}</td>
                        </tr>
                        <tr>
                            <td class="td_title">地址</td>
                            <td colspan="5">${customer.address}</td>
                        </tr>
                        <tr>
                            <td class="td_title">备注</td>
                            <td colspan="5">${customer.mark}</td>
                        </tr>
                    </table>
                </div>
                <div class="box-footer">
                    <span style="color: #ccc" title="<fmt:formatDate value="${customer.createTime}"/>" class="pull-right">创建日期：<fmt:formatDate value="${customer.createTime}"/> &nbsp;&nbsp;&nbsp;&nbsp;
                        最后修改日期：<fmt:formatDate value="${customer.updateTime}"/></span>
                </div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">跟进记录<small style="margin-left: 5px;"><button id="showRecordModalBtn" style="margin-bottom:5px"  class="btn btn-success btn-xs"><i class="fa fa-plus"></i></button></small></h3>
                            <ul class="timeline">

                                <c:forEach items="${saleChanceRecordList}" var="record">
                                    <c:choose>
                                        <c:when test="${record.content == '当前进度修改为[成交]'}">
                                            <li>
                                                <i class="fa fa-check bg-green"></i>
                                                <div class="timeline-item">
                                                    <span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${record.createTime}"/></span>
                                                    <div class="timeline-body">
                                                            ${record.content}
                                                    </div>
                                                </div>
                                            </li>
                                        </c:when>
                                        <c:when test="${record.content == '当前进度修改为[暂时搁置]'}">
                                            <li>
                                                <i class="fa fa-close bg-red"></i>
                                                <div class="timeline-item">
                                                    <span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${record.createTime}"/></span>
                                                    <div class="timeline-body">
                                                            ${record.content}
                                                    </div>
                                                </div>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <i class="fa fa-info bg-blue"></i>
                                                <div class="timeline-item">
                                                    <span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${record.createTime}"/></span>
                                                    <div class="timeline-body">
                                                            ${record.content}
                                                    </div>
                                                </div>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>

                                </c:forEach>
                            </ul>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">日程安排 <a href="/task/new/${customer.id}" id="addTaskBtn" style="margin-bottom:5px"  class="btn btn-success btn-xs"><i class="fa fa-plus"></i></a></h3>
                            <ul>
                                <c:forEach items="${taskList}" var="task">
                                    <p></p>
                                        <div class="timeline-item">
                                            <span>
                                                    <a href="/task/list">${task.title}</a>
                                            </span>
                                            <span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${task.createTime}"/></span>
                                        </div>

                                </c:forEach>
                            </ul>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">相关资料</h3>
                        </div>
                        <div class="box-body">

                        </div>
                    </div>
                </div>
            </div>

        <%--跟进记录--%>
        <div class="modal fade" id="recordModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title">添加跟进记录</h4>
                        </div>
                        <div class="modal-body">
                            <form action="/sale/my/new/record" id="recordForm" method="post">
                                <input type="hidden" name="saleId" value="${saleChance.id}">
                                <textarea id="recordContent"  class="form-control" name="content"></textarea>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" id="saveRecordBtn">保存</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->

        </section>
        <!-- /.content -->
        <%--用户选择对话框（转交他人）--%>
        <div class="modal fade" id="chooseUserModel">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">请选择转入账号</h4>
                    </div>
                    <div class="modal-body">
                        <select id="userSelect" class="form-control">
                            <c:forEach items="${userList}" var="user">
                                <c:if test="${user.id != customer.userId}">
                                    <option value="${user.id}">${user.userName} (${user.mobile})</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" id="saveTranBtn">确定</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <%@include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    $(function () {

        var customerId = ${customer.id};

        $("#delete").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除吗",function (index) {
                window.location.href="/customer/delete/"+id;
                layer.close(index);
            })
        })

        //转交他人
        $("#changeUser").click(function () {
            $("#chooseUserModel").modal({
                show:true,
                backdrop:'static'
            });
        });
        $("#saveTranBtn").click(function () {
            var toUserId = $("#userSelect").val();
            var toUserName = $("#userSelect option:selected").text();
            layer.confirm("确定要将客户转交给"+toUserName+"吗",function (index) {
                layer.close(index);
                window.location.href = "/customer/my/"+customerId+"/to/"+toUserId;
            });
        });
        
        $("#public").click(function () {
            layer.confirm("确定要放入公海吗",function (index) {
                layer.close(index);
                window.location.href = "/customer/my/"+customerId+"/public";
            })
        })


        $("#showRecordModalBtn").click(function () {
            $("#recordModal").modal({
                show:true,
                backdrop:'static'
            });
        });





    });
</script>

</body>
</html>
