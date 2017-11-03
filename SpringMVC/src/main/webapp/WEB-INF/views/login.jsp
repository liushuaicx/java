<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title></title>
    <link rel="stylesheet" href="/static/css/bootstrap.css"/>
</head>
<body>

    <div class="container">
        <form action="/login" method="post" enctype="multipart/form-data">
            <legend>欢迎登录</legend>
            <c:if test="${not empty message}">
                <div class="alert alert-success">
                        ${message}
                </div>
            </c:if>
            <div class="form-group">
                <label>账号</label>
                <input type="text" name="name" class="form-control">
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="text" name="password" class="form-control">
            </div>
            <div class="form-group">
                <label>上传照片</label>
                <<input type="file" name="photo" class="form-control">
            </div>
            <button class="btn btn-primary">登录</button>
        </form>

    </div>



</body>
</html>