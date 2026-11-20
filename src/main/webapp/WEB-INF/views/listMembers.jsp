<%--
  Created by IntelliJ IDEA.
  User: kamog
  Date: 21/11/2026
  Time: 00:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Members - LIBIS</title>
</head>
<body style="font-family: Arial; background-color: #f0f0f0; padding: 20px;">
    <div style="background-color: white; max-width: 900px; margin: 0 auto; padding: 20px; border: 1px solid #ccc;">

        <div style="display: flex; justify-content: space-between; border-bottom: 2px solid #2d5a27; padding-bottom: 10px;">
            <h2>Manage Members</h2>
            <a href="welcome">Back to Catalogue</a>
        </div>

        <!-- Add Member Form -->
        <div style="background-color: #e8f5e9; padding: 15px; margin-top: 20px;">
            <h3>Add New Member</h3>
            <form action="manageMembers" method="post">
                <input type="text" name="fullName" placeholder="Full Name" required>
                <input type="email" name="email" placeholder="Email" required>
                <input type="text" name="username" placeholder="Username" required>
                <button type="submit" style="background-color: #2d5a27; color: white; padding: 5px 10px;">Add Member</button>
            </form>
        </div>

        <!-- Members Table -->
        <table style="width: 100%; border-collapse: collapse; margin-top: 20px;">
            <tr style="background-color: #2d5a27; color: white;">
                <th style="padding: 10px; text-align: left;">Name</th>
                <th style="padding: 10px; text-align: left;">Email</th>
                <th style="padding: 10px; text-align: left;">Username</th>
                <th style="padding: 10px; text-align: left;">Action</th>
            </tr>
            <c:forEach var="m" items="${memberList}">
                <tr style="border-bottom: 1px solid #ddd;">
                    <td style="padding: 10px;">${m.fullName}</td>
                    <td style="padding: 10px;">${m.email}</td>
                    <td style="padding: 10px;">${m.username}</td>
                    <td style="padding: 10px;">
                        <a href="manageMembers?action=delete&id=${m.memberId}"
                           onclick="return confirm('Are you sure? This will delete all their loan history!');"
                           style="color: red;">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

    </div>
</body>
</html>
