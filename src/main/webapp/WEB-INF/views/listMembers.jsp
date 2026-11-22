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
    <title>Manage Members - LIBIS</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #eef2f5; padding: 40px 20px; margin: 0; color: #333; }
        .container { background-color: white; max-width: 1000px; margin: 0 auto; padding: 30px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 3px solid #2d5a27; padding-bottom: 15px; margin-bottom: 25px; }
        .header h2 { margin: 0; color: #2d5a27; }
        .back-link { color: #2d5a27; text-decoration: none; font-weight: bold; }
        .form-panel { background-color: #f9fbf9; border: 1px solid #c3d6c1; padding: 15px; border-radius: 6px; margin-bottom: 25px; }
        .form-panel h3 { margin-top: 0; color: #2d5a27; font-size: 16px; }
        .form-panel input { padding: 8px; margin-right: 10px; border: 1px solid #ccc; border-radius: 4px; }
        .btn-add { background-color: #2d5a27; color: white; padding: 9px 15px; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; }
        .btn-add:hover { background-color: #1a3c1a; }
        table { width: 100%; border-collapse: collapse; }
        th { background-color: #2d5a27; color: white; padding: 12px; text-align: left; font-weight: 600; }
        td { padding: 12px; border-bottom: 1px solid #e0e0e0; font-size: 14px; }
        tr:hover { background-color: #f9fbf9; }
        .action-links a { text-decoration: none; font-weight: 500; margin-right: 10px; }
        .link-edit { color: #2980b9; }
        .link-delete { color: #c0392b; }
        .action-links a:hover { text-decoration: underline; }
    </style>
</head>

<body>

    <div class="container">
        <div class="header">
            <h2><fmt:message key="members.manage" /></h2>
            <a href="welcome" class="back-link">
                ← <fmt:message key="members.back" />
            </a>
        </div>

        <div class="form-panel">
            <h3><fmt:message key="members.add_new" /></h3>

            <form action="manageMembers" method="post">
                <input type="text" name="fullName"
                       placeholder="<fmt:message key='members.full_name' />"
                       required>

                <input type="email" name="email"
                       placeholder="<fmt:message key='members.email' />"
                       required>

                <input type="text" name="username"
                       placeholder="<fmt:message key='members.username' />"
                       required>

                <button type="submit" class="btn-add">
                    <fmt:message key="members.add" />
                </button>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th><fmt:message key="members.name" /></th>
                    <th><fmt:message key="members.email" /></th>
                    <th><fmt:message key="members.username" /></th>
                    <th><fmt:message key="members.action" /></th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="m" items="${memberList}">
                    <tr>
                        <td>
                            <strong><c:out value="${m.fullName}" /></strong>
                        </td>
                        <td><c:out value="${m.email}" /></td>
                        <td><c:out value="${m.username}" /></td>

                        <td class="action-links">
                            <a href="manageMembers?action=edit&amp;id=${m.memberId}"
                               class="link-edit">
                                <fmt:message key="members.edit" />
                            </a>

                            <a href="manageMembers?action=delete&amp;id=${m.memberId}"
                               onclick="return confirm('<fmt:message key='members.delete_confirm' />');"
                               class="link-delete">
                                <fmt:message key="members.delete" />
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</body>

</html>