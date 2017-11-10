<%--
  Created by IntelliJ IDEA.
  User: 刘帅
  Date: 2017/11/4
  Time: 13:19
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
        <a href="/product">返回列表</a>
        <a href="/product/${kaola.id}/edit" class="pull-right">编辑</a>
        <a href="javaScript:;" id="delete" rel="${kaola.id}" class="pull-right">删除</a>
        <h3>${kaola.productName}

        </h3>
        <ul>
            <li>考拉价: ${kaola.price}</li>
            <li>专柜价: ${kaola.marketPrice}</li>
            <li>产地: ${kaola.place}</li>
            <li>评论数量: ${kaola.commentNum}</li>
        </ul>
    </div>
    <script src="/static/js/jquery.min.js"></script>
    <script>
        $(function () {
           $("#delete").click(function () {
                var id = $(this).attr("rel");
                if (confirm("确定要删除吗")) {
                    window.location.href="/product/" + id +"/delete";
                }

           })
        });

    </script>
</body>
</html>
