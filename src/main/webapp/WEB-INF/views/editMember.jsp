<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Member - LIBIS</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #eef2f5; padding: 40px 20px; margin: 0; color: #333; }
        .container { background-color: white; max-width: 500px; margin: 0 auto; padding: 30px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 3px solid #2d5a27; padding-bottom: 15px; margin-bottom: 25px; }
        .header h2 { margin: 0; color: #2d5a27; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; font-size: 14px; color: #555; }
        .form-group input { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; font-family: inherit; }
        .form-group input:focus { outline: none; border-color: #2d5a27; }
        .actions { margin-top: 25px; display: flex; align-items: center; }
        .btn-save { background-color: #2d5a27; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; font-size: 14px; }
        .btn-save:hover { background-color: #1a3c1a; }
        .btn-cancel { color: #c0392b; text-decoration: none; margin-left: 15px; font-weight: bold; font-size: 14px; }
        .btn-cancel:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h2>Edit Member</h2>
        </div>

        <form action="manageMembers" method="post">
            <!-- Hidden action and ID so the Servlet knows who to update -->
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="memberId" value="${member.memberId}">

            <div class="form-group">
                <label>Full Name:</label>
                <input type="text" name="fullName" value="${member.fullName}" required>
            </div>

            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" value="${member.email}" required>
            </div>

            <div class="form-group">
                <label>Username:</label>
                <input type="text" name="username" value="${member.username}" required>
            </div>

            <div class="actions">
                <button type="submit" class="btn-save">Save Changes</button>
                <a href="manageMembers" class="btn-cancel">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>