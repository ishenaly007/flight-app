<%--
  Created by IntelliJ IDEA.
  User: ishenaly
  Date: 01.04.2024
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <span>Content Русский</span>
    <p>Size ${requestScope.flights.size()}</p>
    <p>Size ${requestScope.flights.get(0).description()}</p><%--non-null safe--%>
    <p>Size ${requestScope.flights[1].id()}</p><%--null safe--%>
</div>
</body>
</html>
