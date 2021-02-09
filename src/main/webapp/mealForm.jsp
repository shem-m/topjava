<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<jsp:useBean id="meal" scope="request"  type="ru.javawebinar.topjava.model.Meal"/>

<form method="post" <%--action="meals?action=edit"--%>>
    <input type="hidden" name="Id" value="${meal.id}">
    DateTime: <input type="datetime-local" name="dateTime" value="${meal.dateTime}" required/> <br/>
    Description: <input type="text" name="description" value="${meal.description}" required/> <br/>
    Calories: <input type="number" name="calories" value="${meal.calories}" required/> <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
