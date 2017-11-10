<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 刘帅
  Date: 2017/11/4
  Time: 9:56
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
        <table>
            <tr>
                <th>文章标题</th>
                <th>创建时间</th>
                <th>回复数量</th>
                <th>分类</th>
            </tr>
            <c:forEach items="${articleList}" var="article">
                <tr>
                    <td>${article.title}</td>
                    <td>${article.createtime}</td>
                    <td>${article.replynum}</td>
                    <td>${article.nodeid}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

</body>
</html>
