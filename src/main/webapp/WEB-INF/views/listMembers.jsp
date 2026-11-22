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
        .form-panel input { padding: 8px; margin-right: 10px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 4px; }
        .btn-add { background-color: #2d5a27; color: white; padding: 9px 15px; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; }
        .btn-add:hover { background-color: #1a3c1a; }
        .search-bar { display: flex; gap: 10px; margin-bottom: 20px; align-items: center; }
        .search-bar input { padding: 10px; border: 1px solid #ccc; border-radius: 4px; flex: 1; }
        table { width: 100%; border-collapse: collapse; }
        th { background-color: #2d5a27; color: white; padding: 12px; text-align: left; font-weight: 600; }
        td { padding: 12px; border-bottom: 1px solid #e0e0e0; font-size: 14px; }
        tr:hover { background-color: #f9fbf9; }
        .action-links a, .action-links button { text-decoration: none; font-weight: 500; margin-right: 10px; background: none; border: none; padding: 0; cursor: pointer; font-family: inherit; font-size: 14px; }
        .link-edit { color: #2980b9; }
        .link-delete { color: #c0392b; }
        .error-box { background-color: #f8d7da; color: #721c24; padding: 15px; margin-bottom: 20px; border-radius: 4px; font-size: 14px; border: 1px solid #f5c6cb;}
        .error-box ul { margin: 0; padding-left: 20px; }
    </style>
</head>
<body>

    <div class="container">
        <div class="header">
            <h2><fmt:message key="members.manage" /></h2>
            <a href="welcome" class="back-link">← <fmt:message key="members.back" /></a>
        </div>

        <!-- Escaped Error Messages -->
        <c:if test="${not empty errorList}">
            <div class="error-box">
                <ul>
                    <c:forEach var="error" items="${errorList}">
                        <li><c:out value="${error}" /></li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <!-- Search Form -->
        <form action="manageMembers" method="get" class="search-bar">
            <input type="search" name="search" placeholder="<fmt:message key='members.search_placeholder' />" value="<c:out value='${param.search}' />">
            <button type="submit" class="btn-add"><fmt:message key="members.search" /></button>
            <a href="manageMembers" class="back-link"><fmt:message key="members.show_all" /></a>
        </form>

        <!-- Add Member Form (Retains values) -->
        <div class="form-panel">
            <h3><fmt:message key="members.add_new" /></h3>
            <form action="manageMembers" method="post">
                <input type="hidden" name="action" value="create">

                <input type="text" name="memberNumber" placeholder="<fmt:message key='members.member_number' />" value="<c:out value='${member.memberNumber}' />" required>
                <input type="text" name="fullName" placeholder="<fmt:message key='members.full_name' />" value="<c:out value='${member.fullName}' />" required>
                <input type="email" name="email" placeholder="<fmt:message key='members.email' />" value="<c:out value='${member.email}' />" required>
                <input type="text" name="username" placeholder="<fmt:message key='members.username' />" value="<c:out value='${member.username}' />" required>

                <!-- Password is intentionally left blank -->
                <input type="password" name="password" placeholder="<fmt:message key='members.password' />" required>

                <button type="submit" class="btn-add"><fmt:message key="members.add" /></button>
            </form>
        </div>

        <!-- Members Table -->
        <table>
            <thead>
                <tr>
                    <th><fmt:message key="members.member_number" /></th>
                    <th><fmt:message key="members.name" /></th>
                    <th><fmt:message key="members.email" /></th>
                    <th><fmt:message key="members.username" /></th>
                    <th><fmt:message key="members.action" /></th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty memberList}">
                    <tr>
                        <td colspan="5" style="text-align: center; padding: 20px;"><fmt:message key="members.no_members" /></td>
                    </tr>
                </c:if>
                <c:forEach var="m" items="${memberList}">
                    <tr>
                        <td><strong><c:out value="${m.memberNumber}" /></strong></td>
                        <td><c:out value="${m.fullName}" /></td>
                        <td><c:out value="${m.email}" /></td>
                        <td><c:out value="${m.username}" /></td>
                        <td class="action-links">
                            <a href="manageMembers?action=edit&id=${m.memberId}" class="link-edit">
                                <fmt:message key="members.edit" />
                            </a>

                            <!-- POST deletion with translated confirmation -->
                            <form action="manageMembers" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${m.memberId}">
                                <button type="submit" class="link-delete"
                                        onclick="return confirm('<fmt:message key="members.delete_confirm" />');">
                                    <fmt:message key="members.delete" />
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