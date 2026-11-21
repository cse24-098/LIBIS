<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="${param.lang != null ? param.lang : 'en'}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html>
<head>
    <title>LIBIS Catalogue</title>
</head>
<body style="font-family: Arial; background-color: #f0f0f0; padding: 20px;">

    <div style="background-color: white; max-width: 900px; margin: 0 auto; padding: 20px; border: 1px solid #ccc;">

        <div style="display: flex; justify-content: space-between; border-bottom: 2px solid #2d5a27; padding-bottom: 10px;">
            <h2><fmt:message key="catalogue.title" /></h2>
            <div>
                <span><fmt:message key="catalogue.welcome" />, <strong>${loggedInMember.fullName}</strong>!</span>
                | <a href="myLoans" style="font-weight: bold; color: #2d5a27;"><fmt:message key="nav.my_loans" /></a>
                | <a href="logout"><fmt:message key="nav.logout" /></a>
                | <a href="?lang=${param.lang == 'tn' ? 'en' : 'tn'}"><fmt:message key="nav.lang" /></a>
            </div>
        </div>

        <table style="width: 100%; border-collapse: collapse; margin-top: 20px;">
            <thead>
                <tr style="background-color: #2d5a27; color: white;">
                    <th style="padding: 10px; text-align: left;"><fmt:message key="catalogue.title_col" /></th>
                    <th style="padding: 10px; text-align: left;"><fmt:message key="catalogue.author_col" /></th>
                    <th style="padding: 10px; text-align: left;"><fmt:message key="catalogue.genre_col" /></th>
                    <th style="padding: 10px; text-align: left;"><fmt:message key="catalogue.status_col" /></th>
                    <th style="padding: 10px; text-align: left;"><fmt:message key="catalogue.actions_col" /></th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty booksList}">
                    <tr>
                        <td colspan="5" style="padding: 10px; text-align: center;">No books found.</td>
                    </tr>
                </c:if>

                <c:forEach var="book" items="${booksList}">
                    <tr style="border-bottom: 1px solid #ddd;">
                        <td style="padding: 10px;">${book.title}</td>
                        <td style="padding: 10px;">${book.author}</td>
                        <td style="padding: 10px;">${book.genre}</td>

                        <td style="padding: 10px;">
                            <c:choose>
                                <c:when test="${book.available}">
                                    <span style="color: green;"><fmt:message key="catalogue.available" /></span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: red;"><fmt:message key="catalogue.borrowed" /></span>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td style="padding: 10px;">
                            <a href="search?query=${book.title}"><fmt:message key="catalogue.search" /></a> |

                            <c:if test="${book.available}">
                                <a href="borrow?id=${book.bookId}"><fmt:message key="catalogue.borrow" /></a> |
                            </c:if>

                            <a href="#"><fmt:message key="catalogue.reserve" /></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div style="margin-top: 20px;">
            <a href="manageMembers">Manage Members (Librarian)</a> |
            <a href="manageBooks">Manage Books (Librarian)</a>
        </div>

    </div>

</body>
</html>