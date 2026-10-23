<%--
  Created by IntelliJ IDEA.
  User: kamog
  Date: 18/10/2026
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>LIBIS Catalogue</title>
</head>
<body style="font-family: Arial; background-color: #f0f0f0; padding: 20px;">

    <div style="background-color: white; max-width: 900px; margin: 0 auto; padding: 20px; border: 1px solid #ccc;">

        <div style="display: flex; justify-content: space-between; border-bottom: 2px solid #2d5a27; padding-bottom: 10px;">
            <h2>LIBIS Book Catalogue</h2>
            <h3>Welcome, ${loggedInMember.fullName}!</h3>
        </div>

        <table style="width: 100%; border-collapse: collapse; margin-top: 20px;">
            <thead>
                <tr style="background-color: #2d5a27; color: white;">
                    <th style="padding: 10px; text-align: left;">Title</th>
                    <th style="padding: 10px; text-align: left;">Author</th>
                    <th style="padding: 10px; text-align: left;">Genre</th>
                    <th style="padding: 10px; text-align: left;">Status</th>
                    <th style="padding: 10px; text-align: left;">Actions</th>
                </tr>
            </thead>
            <tbody>
                <!-- If the list is totally empty, show a message -->
                <c:if test="${empty booksList}">
                    <tr>
                        <td colspan="5" style="padding: 10px; text-align: center;">No books found. Please log in again.</td>
                    </tr>
                </c:if>

                <!-- Loop through the books -->
                <c:forEach var="book" items="${booksList}">
                    <tr style="border-bottom: 1px solid #ddd;">
                        <td style="padding: 10px;">${book.title}</td>
                        <td style="padding: 10px;">${book.author}</td>
                        <td style="padding: 10px;">${book.genre}</td>

                        <td style="padding: 10px;">
                            <c:choose>
                                <c:when test="${book.available}">
                                    <span style="color: green;">Available</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: red;">Borrowed</span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td style="padding: 10px;">
                            <a href="search?query=${book.title}">Search</a> |
                            <a href="#">Borrow</a> |
                            <a href="#">Reserve</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div style="margin-top: 20px;">
            <a href="login.jsp">Logout</a>
        </div>
    </div>

</body>
</html>