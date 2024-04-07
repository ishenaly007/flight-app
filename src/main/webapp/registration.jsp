<%--
  Created by IntelliJ IDEA.
  User: ishenaly
  Date: 03.04.2024
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Регистрация</h1>
<form action="/registration" method="post">
    <label for="name">Имя:</label>
    <input type="text" id="name" name="name" >
    <br>
    <label for="birthday">Дата рождения:</label>
    <input type="date" id="birthday" name="birthday" >
    <br>
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" >
    <br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" >
    <br>
    <label for="role">Роль:</label>
    <select id="role" name="role" >
        <c:forEach var="role" items="${requestScope.roles}">
            <option value="${role}">${role}</option>
        </c:forEach>
    </select>
    <br>
    <label>Пол:</label>
    <c:forEach var="gender" items="${requestScope.genders}">
        <input type="radio" name="gender" value="${gender}">${gender}</input>
    </c:forEach>
    <br><br>
    <input type="submit" value="Зарегистрироваться">
</form>
<c:if test="${not empty requestScope.errors}">
    <div style="color: red">
        <c:forEach var="error" items="${requestScope.errors}">
            <span>${error.message}</span>
            <br>
        </c:forEach>
    </div>

</c:if>
</body>
</html>
