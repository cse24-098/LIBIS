<%--
  Created by IntelliJ IDEA.
  User: kamog
  Date: 23/10/2026
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>LIBIS Search Results</title>
</head>
<body style="font-family: Arial; background-color: #f0f0f0; padding: 20px;">

    <div style="background-color: white; max-width: 900px; margin: 0 auto; padding: 20px; border: 1px solid #ccc;">

        <h2>Search Results</h2>

        <!-- Show what they searched for -->
        <p>You searched for: <strong>${searchQuery}</strong></p>

        <table style="width: 100%; border-collapse: collapse; margin-top: 20px;">
            <thead>
                <tr style="background-color: #2d5a27; color: white;">
                    <th style="padding: 10px; text-align: left;">Title</th>
                    <th style="padding: 10px; text-align: left;">Author</th>
                    <th style="padding: 10px; text-align: left;">Genre</th>
                    <th style="padding: 10px; text-align: left;">Status</th>
                </tr>
            </thead>
            <tbody>
                <!-- If no results found, show a message -->
                <c:if test="${empty searchResults}">
                    <tr>
                        <td colspan="4" style="padding: 10px; text-align: center; color: red;">No books found matching your search.</td>
                    </tr>
                </c:if>

                <!-- Loop through the search results -->
                <c:forEach var="book" items="${searchResults}">
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
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div style="margin-top: 20px;">
            <!-- Remember, welcome.jsp is in WEB-INF, so we link to the controller -->
            <a href="welcome">Back to Catalogue</a>
        </div>
    </div>

</body>
</html>
