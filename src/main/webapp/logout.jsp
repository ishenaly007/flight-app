<%--
  Created by IntelliJ IDEA.
  User: ishenaly
  Date: 07.04.2024
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<div>
    <c:if test="${not empty sessionScope.user}">
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit">Выйти из аккаунта</button>
        </form>
    </c:if>
</div>