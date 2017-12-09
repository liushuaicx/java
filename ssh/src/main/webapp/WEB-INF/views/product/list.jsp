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

        <div class="panel panel-default">
            <div class="panel-body">
                <form action="" class="form-inline">
                    <input id="productName" value="${param.q_like_s_productName}" type="text" placeholder="商品名称" class="form-control" name="q_like_s_productName">
                    <input id="price" value="${param.q_eq_bd_price_or_marketPrice}" type="text" placeholder="价格 或 市场价格" class="form-control" name="q_eq_bd_price_or_marketPrice">
                    <button class="btn btn-default">搜索</button>
                </form>
            </div>
        </div>
        <p></p>
        <a class="btn btn-primary" href="/product/new">添加商品</a>
        <c:if test="${not empty param}">
            <a class="btn btn-default" href="/product">返回</a>
        </c:if>
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
                <c:forEach items="${page.items}" var="product">
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
        <ul id="pagination" class="pagination pagination-lg"></ul>
    </div>

<script src="/static/js/jquery-2.2.3.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"/>
<script>
    $(function(){

        $("#pagination").twbsPagination({
            totalPages:"${page.pageTotal}",
            visiblePages:5,
            href:"/product?p={{number}}&q_like_s_productName=${param.q_like_s_productName}&q_eq_bd_price_or_marketPrice=${param.q_eq_bd_price_or_marketPrice}",
            first: "首页",
            prev: "上一页",
            next:"下一页",
            last:"末页"
        });
    });


</script>

</body>
</html>