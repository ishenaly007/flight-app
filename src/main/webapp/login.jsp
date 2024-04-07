<%--
  Created by IntelliJ IDEA.
  User: ishenaly
  Date: 07.04.2024
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" value="${param.email}" required>
    <br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>
    <br>
    <input type="submit" value="Войти">
    <a href="${pageContext.request.contextPath}/registration">
        <button type="button">Register</button>
    </a>
    <c:if test="${param.error != null}">
        <div style="color: red">
            <span>Email or password is not correct!</span>
        </div>
    </c:if>
</form>
</body>
</html>
