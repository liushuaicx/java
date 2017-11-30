<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title></title>
</head>
<body>

    <h3>hello,qiniu!</h3>
    <form action="http://upload-z1.qiniup.com" method="post" enctype="multipart/form-data">
        <input type="hidden" name="token" value="${upToken}">
        <input type="file" name="file">
        <input type="text" name="fileName">
        <button>上传</button>
    </form>

</body>
</html>