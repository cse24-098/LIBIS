<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:if test="${param.lang == 'en' || param.lang == 'tn'}">
    <c:set var="selectedLanguage" value="${param.lang}" scope="session" />
</c:if>

<c:if test="${empty sessionScope.selectedLanguage}">
    <c:set var="selectedLanguage" value="en" scope="session" />
</c:if>

<fmt:setLocale value="${sessionScope.selectedLanguage}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="nav.my_loans" /> - LIBIS</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #eef2f5; padding: 40px 20px; margin: 0; color: #333; }
        .container { background-color: white; max-width: 1000px; margin: 0 auto; padding: 30px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 3px solid #2d5a27; padding-bottom: 15px; margin-bottom: 25px; }
        .header h2 { margin: 0; color: #2d5a27; }
        .back-link { color: #2d5a27; text-decoration: none; font-weight: bold; }
        .back-link:hover { text-decoration: underline; }
        .loan-status { font-size: 16px; margin-bottom: 20px; }
        .warning { color: #c0392b; font-weight: bold; }
        table { width: 100%; border-collapse: collapse; }
        th { background-color: #2d5a27; color: white; padding: 12px; text-align: left; font-weight: 600; }
        td { padding: 12px; border-bottom: 1px solid #e0e0e0; font-size: 14px; }
        tr:hover { background-color: #f9fbf9; }
        .btn-return { background-color: #c0392b; color: white; padding: 6px 12px; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; font-size: 12px; }
        .btn-return:hover { background-color: #962d22; }
        .no-loans { text-align: center; padding: 20px; color: #555; }
    </style>
</head>
<body>

    <div class="container">
        <div class="header">
            <h2><fmt:message key="nav.my_loans" /></h2>
            <a href="welcome" class="back-link">← <fmt:message key="catalogue.title" /></a>
        </div>


        <div class="loan-status">
            <fmt:message key="loans.status">
                <fmt:param value="${loanCount}" />
            </fmt:message>

            <c:if test="${loanCount >= 3}">
                <span class="warning">
                    <fmt:message key="loans.limit" />
                </span>
            </c:if>
        </div>

        <table>
            <thead>
                <tr>
                    <th><fmt:message key="catalogue.title_col" /></th>
                    <th><fmt:message key="catalogue.author_col" /></th>
                    <th><fmt:message key="catalogue.actions_col" /></th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty myBooks}">
                    <tr>
                        <td colspan="3" class="no-loans">
                            <fmt:message key="loans.empty" />
                        </td>
                    </tr>
                </c:if>

                <c:forEach var="book" items="${myBooks}">
                    <tr>
                        <td><strong>${book.title}</strong></td>
                        <td>${book.author}</td>
                        <td>
                            <form action="return" method="post" style="margin: 0;">
                                <input type="hidden" name="bookId" value="${book.bookId}">
                                <button type="submit" class="btn-return">
                                    <fmt:message key="loans.return" />
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</body>
</html>