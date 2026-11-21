<%--
  Created by IntelliJ IDEA.
  User: kamog
  Date: 21/11/2026
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Member</title>
</head>
<body style="font-family: Arial; padding: 20px; background-color: #f0f0f0;">
    <div style="background: white; max-width: 500px; margin: 0 auto; padding: 20px; border: 1px solid #ccc;">
        <h2>Edit Member</h2>

        <form action="manageMembers" method="post">
            <!-- Hidden action and ID so the Servlet knows who to update -->
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="memberId" value="${member.memberId}">

            <p>Full Name:</p>
            <input type="text" name="fullName" value="${member.fullName}" required style="width: 100%; padding: 5px;">

            <p>Email:</p>
            <input type="email" name="email" value="${member.email}" required style="width: 100%; padding: 5px;">

            <p>Username:</p>
            <input type="text" name="username" value="${member.username}" required style="width: 100%; padding: 5px;">

            <br><br>
            <button type="submit" style="background: #2d5a27; color: white; padding: 10px 20px; border: none;">Save Changes</button>
            <a href="manageMembers" style="margin-left: 10px;">Cancel</a>
        </form>
    </div>
</body>
</html>