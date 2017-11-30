<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <title></title>
</head>
<body>
    <div class="container">
        <p></p>
        <a class="btn btn-primary" href="/product/new">添加商品</a>
        <table class="table">
            <thead>
                <tr>
                    <th>商品名称</th>
                    <th>考拉价</th>
                    <th>市场价</th>
                    <th>产地</th>
                    <th>评论数量</th>
                    <th>类型</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${productList}" var="product">
                    <tr>
                        <td>${product.productName}</td>
                        <td>${product.price}</td>
                        <td>${product.marketPrice}</td>
                        <td>${product.place}</td>
                        <td>${product.commentNum}</td>
                        <td>${product.productType.typeName}</td>
                        <td>
                            <a href="/product/delete/${product.id}">删除</a>
                            <a href="/product/update/${product.id}">修改</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>

        </table>
    </div>
</body>
</html>