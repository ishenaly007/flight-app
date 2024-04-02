<%--
  Created by IntelliJ IDEA.
  User: ishenaly
  Date: 01.04.2024
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.abit8.service.TicketService" %>
<%@ page import="com.abit8.jdbc.dto.TicketDto" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Купленные билеты:</h1>
<ul>
    <c:forEach var="ticket" items="${requestScope.tickets}">
        <li>${ticket.passengerName()}</li>
    </c:forEach>
</ul>
</body>
</html>