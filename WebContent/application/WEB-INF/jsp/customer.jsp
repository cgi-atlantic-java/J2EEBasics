<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setTimeZone value="Z"/>
<jsp:useBean id="now" class="java.util.Date"/>

<!DOCTYPE html>
<html>
  <head>
    <title>Customer</title>
  </head>
  <body>
    <h1>Customer</h1>
    <p><fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm:ss" value="${now}"/></p>
  </body>
</html>
