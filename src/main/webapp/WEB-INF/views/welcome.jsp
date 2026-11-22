<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="${param.lang != null ? param.lang : 'en'}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html>
<head>
    <title>LIBIS Catalogue</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #eef2f5;
            padding: 40px 20px;
            margin: 0;
            color: #333;
        }
        .container {
            background-color: white;
            max-width: 1000px;
            margin: 0 auto;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 3px solid #2d5a27;
            padding-bottom: 15px;
            margin-bottom: 25px;
        }
        .header h2 {
            margin: 0;
            color: #2d5a27;
        }
        .nav-links a {
            color: #2d5a27;
            text-decoration: none;
            font-weight: bold;
            margin-left: 15px;
            font-size: 14px;
        }
        .nav-links a:hover {
            text-decoration: underline;
        }
        .user-greeting {
            font-size: 14px;
            color: #555;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th {
            background-color: #2d5a27;
            color: white;
            padding: 12px;
            text-align: left;
            font-weight: 600;
        }
        td {
            padding: 12px;
            border-bottom: 1px solid #e0e0e0;
            font-size: 14px;
        }
        tr:hover {
            background-color: #f9fbf9;
        }
        .badge {
            padding: 4px 10px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
        }
        .badge-green { background-color: #d4edda; color: #155724; }
        .badge-red { background-color: #f8d7da; color: #721c24; }
        .action-links a {
            color: #2d5a27;
            text-decoration: none;
            margin-right: 10px;
            font-weight: 500;
        }
        .action-links a:hover {
            text-decoration: underline;
        }
        .admin-panel {
            margin-top: 30px;
            padding: 15px;
            background-color: #f8f9fa;
            border: 1px dashed #2d5a27;
            border-radius: 4px;
        }
        .admin-panel h3 {
            margin-top: 0;
            font-size: 14px;
            color: #2d5a27;
        }
        .admin-panel a {
            display: inline-block;
            background-color: #2d5a27;
            color: white;
            padding: 8px 15px;
            text-decoration: none;
            border-radius: 4px;
            margin-right: 10px;
            font-size: 13px;
        }
        .admin-panel a:hover {
            background-color: #1a3c1a;
        }
    </style>
</head>
<body>

    <div class="container">
        <div class="header">
            <h2><fmt:message key="catalogue.title" /></h2>
            <div class="nav-links">
                <span class="user-greeting"><fmt:message key="catalogue.welcome" />, <strong>${loggedInMember.fullName}</strong></span>
                <a href="myLoans"><fmt:message key="nav.my_loans" /></a>
                <a href="logout"><fmt:message key="nav.logout" /></a>
                <a href="?lang=${param.lang == 'tn' ? 'en' : 'tn'}"><fmt:message key="nav.lang" /></a>
            </div>
        </div>

        <table>
            <thead>
                <tr>
                    <th><fmt:message key="catalogue.title_col" /></th>
                    <th><fmt:message key="catalogue.author_col" /></th>
                    <th><fmt:message key="catalogue.genre_col" /></th>
                    <th><fmt:message key="catalogue.status_col" /></th>
                    <th><fmt:message key="catalogue.actions_col" /></th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty booksList}">
                    <tr>
                        <td colspan="5" style="text-align: center; padding: 20px;">No books found.</td>
                    </tr>
                </c:if>

                <c:forEach var="book" items="${booksList}">
                    <tr>
                        <td><strong>${book.title}</strong></td>
                        <td>${book.author}</td>
                        <td>${book.genre}</td>
                        <td>
                            <c:choose>
                                <c:when test="${book.available}">
                                    <span class="badge badge-green"><fmt:message key="catalogue.available" /></span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-red"><fmt:message key="catalogue.borrowed" /></span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="action-links">
                            <a href="search?query=${book.title}"><fmt:message key="catalogue.search" /></a>
                            <c:if test="${book.available}">
                                <a href="borrow?id=${book.bookId}"><fmt:message key="catalogue.borrow" /></a>
                            </c:if>
                            <a href="#"><fmt:message key="catalogue.reserve" /></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- ONLY SHOW THIS IF THE USER IS A LIBRARIAN -->
        <c:if test="${loggedInMember.role == 'librarian'}">
            <div class="admin-panel">
                <h3>Librarian Administration</h3>
                <a href="manageMembers">Manage Members</a>
                <a href="manageBooks">Manage Books</a>
            </div>
        </c:if>

    </div>

</body>
</html>