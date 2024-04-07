<%--
  Created by IntelliJ IDEA.
  User: ishenaly
  Date: 01.04.2024
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<%@include file="logout.jsp"%>
<body>
<h1>Список перелетов:</h1>
<ul>
    <c:forEach var="flight" items="${requestScope.flights}">
        <li>
            <a href="<c:url value="/tickets?flightId=${flight.id()}"/>">${flight.description()}</a>
        </li>
    </c:forEach>
</ul>
</body>
</html>
