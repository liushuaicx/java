<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 刘帅
  Date: 2017/11/4
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Title</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">查询</h3>
            </div>
            <div class="panel-body">
                <form class="form-inline">
                    <input type="text" name="productName" placeholder="商品名称" class="form-control" value="${param.productName}">
                    <select name="place" class="form-control">
                        <option value="">--选择产地--</option>
                        <c:forEach items="${place}" var="place">
                            <option value="${place}" ${param.place == place ? 'selected' : ''}>${place}</option>
                        </c:forEach>
                    </select>
                    <select name="typeId" class="form-control">
                        <option value="">--选择分类--</option>
                        <c:forEach items="${typeList}" var="type">
                            <option value="${type.id}" ${param.typeId == type.id ? 'selected' : ''}>${type.typeName}</option>
                        </c:forEach>
                    </select>
                    <button class="btn btn-default">搜索</button>
                </form>
            </div>

        </div>



        <a href="/product/add">添加</a>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <table class="table">
            <tr>
                <th>商品名称</th>
                <th>类型</th>
                <th>考拉价</th>
                <th>专柜价</th>
                <th>产地</th>
                <th>评论数量</th>
            </tr>
            <c:forEach items="${pageInfo.list}" var="kaola">
                <tr>
                    <td><a href="/product/${kaola.id}">${kaola.productName}</a></td>
                    <td>${kaola.kaolaType.typeName}</td>
                    <td>${kaola.price}</td>
                    <td>${kaola.marketPrice}</td>
                    <td>${kaola.place}</td>
                    <td>${kaola.commentNum}</td>
                </tr>
            </c:forEach>
        </table>
        <ul id="pagination-demo" class="pagination-sm"></ul>
    </div>
    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
    <script src="/static/js/jquery.twbsPagination.min.js"></script>
    <script>
        $(function(){
            //分页
            $('#pagination-demo').twbsPagination({
                totalPages: ${pageInfo.pages},
                visiblePages: 10,
                first:'首页',
                last:'末页',
                prev:'上一页',
                next:'下一页',
                href:"?p={{number}}&productName="+encodeURIComponent('${param.productName}')+"&place="+encodeURIComponent('${param.place}')+"&typeId=${param.typeId}"
            });
        });
    </script>

</body>
</html>
