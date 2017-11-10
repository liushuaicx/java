<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 刘帅
  Date: 2017/11/4
  Time: 16:41
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
    <form method="post" class="form-group">
        <div>
            <legend>所属分类</legend>
            <select name="typeId" class="form-control">
                <option value="">--请选择类型--</option>
                <c:forEach items="${kaolaType}" var="type">
                    <option value="${type.id}">${type.typeName}</option>
                </c:forEach>
            </select>

        </div>
        <div>
            <legend>商品名称</legend>
            <input type="text" class="form-control" name="productName">
        </div>
        <div>
            <legend>考拉价</legend>
            <input type="text" class="form-control" name="price">
        </div>
        <div>
            <legend>专柜价</legend>
            <input type="text" class="form-control" name="marketPrice">
        </div>
        <div>
            <legend>产地</legend>
            <input type="text" class="form-control" name="place">
        </div>
        <p></p>
        <div>
            <button class="btn btn-primary">保存</button>
        </div>
    </form>

</div>

</body>
</html>
