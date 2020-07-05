<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <title>404</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/bootstrap.min.css" th:href="@{/css/bootstrap.min.css}" >
</head>
<body>
    <div class="container" style="max-width: 600px;margin-top: 100px;">

        <div class="jumbotron">
            <h2>404</h2>
            <p>很抱歉，你訪問的資源[<code th:text="${#httpServletRequest.getAttribute('javax.servlet.error.request_uri')}"></code>]不存在</p>
        </div>

    </div>
</body>
</html>