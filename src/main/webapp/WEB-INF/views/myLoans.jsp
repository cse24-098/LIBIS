<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<fmt:setLocale value="${param.lang != null ? param.lang : 'en'}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html>
<head>
    <title>My Loans - LIBIS</title>
</head>
<body style="font-family: Arial; background-color: #f0f0f0; padding: 20px;">

    <div style="background-color: white; max-width: 900px; margin: 0 auto; padding: 20px; border: 1px solid #ccc;">

        <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #2d5a27; padding-bottom: 10px;">
            <h2><fmt:message key="nav.my_loans" /></h2>
            <a href="welcome"><fmt:message key="catalogue.title" /></a>
        </div>

        <h3>You currently have ${loanCount} out of 3 allowed books.</h3>
        <c:if test="${loanCount >= 3}">
            <p style="color: red;">You have reached the maximum limit!</p>
        </c:if>

        <table style="width: 100%; border-collapse: collapse; margin-top: 20px;">
            <thead>
                <tr style="background-color: #2d5a27; color: white;">
                    <th style="padding: 10px; text-align: left;"><fmt:message key="catalogue.title_col" /></th>
                    <th style="padding: 10px; text-align: left;"><fmt:message key="catalogue.author_col" /></th>
                    <th style="padding: 10px; text-align: left;"><fmt:message key="catalogue.actions_col" /></th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty myBooks}">
                    <tr>
                        <td colspan="3" style="padding: 20px; text-align: center;">You have no books out right now.</td>
                    </tr>
                </c:if>

                <c:forEach var="book" items="${myBooks}">
                    <tr style="border-bottom: 1px solid #ddd;">
                        <td style="padding: 10px;">${book.title}</td>
                        <td style="padding: 10px;">${book.author}</td>
                        <td style="padding: 10px;">
                            <form action="return" method="post" style="margin: 0;">
                                <input type="hidden" name="bookId" value="${book.bookId}">
                                <button type="submit" style="background-color: #c0392b; color: white; padding: 5px 10px; border: none; cursor: pointer;">
                                    Return
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