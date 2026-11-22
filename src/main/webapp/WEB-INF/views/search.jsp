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
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #eef2f5; padding: 40px 20px; margin: 0; color: #333; }
        .container { background-color: white; max-width: 1000px; margin: 0 auto; padding: 30px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 3px solid #2d5a27; padding-bottom: 15px; margin-bottom: 25px; }
        .header h2 { margin: 0; color: #2d5a27; }
        .back-link { color: #2d5a27; text-decoration: none; font-weight: bold; }
        .back-link:hover { text-decoration: underline; }
        .search-info { background-color: #f9fbf9; border-left: 4px solid #2d5a27; padding: 10px 15px; margin-bottom: 20px; font-size: 14px; }
        table { width: 100%; border-collapse: collapse; }
        th { background-color: #2d5a27; color: white; padding: 12px; text-align: left; font-weight: 600; }
        td { padding: 12px; border-bottom: 1px solid #e0e0e0; font-size: 14px; }
        tr:hover { background-color: #f9fbf9; }
        .badge { padding: 4px 10px; border-radius: 4px; font-size: 12px; font-weight: bold; }
        .badge-green { background-color: #d4edda; color: #155724; }
        .badge-red { background-color: #f8d7da; color: #721c24; }
        .no-results { text-align: center; color: #c0392b; padding: 20px; }
    </style>
</head>
<body>

    <div class="container">
        <div class="header">
            <h2>Search Results</h2>
            <a href="welcome" class="back-link">← Back to Catalogue</a>
        </div>

        <div class="search-info">
            You searched for: <strong>${searchQuery}</strong>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Genre</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty searchResults}">
                    <tr>
                        <td colspan="4" class="no-results">No books found matching your search.</td>
                    </tr>
                </c:if>

                <c:forEach var="book" items="${searchResults}">
                    <tr>
                        <td><strong>${book.title}</strong></td>
                        <td>${book.author}</td>
                        <td>${book.genre}</td>
                        <td>
                            <c:choose>
                                <c:when test="${book.available}">
                                    <span class="badge badge-green">Available</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-red">Borrowed</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</body>
</html>