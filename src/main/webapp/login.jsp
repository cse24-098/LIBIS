<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>LIBIS Login</title>
</head>
<body style="font-family: Arial; background-color: #f0f0f0; text-align: center; padding-top: 50px;">

    <div style="background-color: white; width: 300px; margin: 0 auto; padding: 30px; border: 1px solid #ccc;">
        <h2>LIBIS Login</h2>

        <!-- Show error message if login fails -->
        <% if (request.getAttribute("error") != null) { %>
            <p style="color: red;"><%= request.getAttribute("error") %></p>
        <% } %>

        <!-- Post to your custom LoginServlet, not j_security_check -->
        <form action="j_security_check" method="post">
            <div style="margin-bottom: 15px; text-align: left;">
                <label>Username:</label><br>
                <input type="text" id="username" name="j_username" required style="width: 100%; padding: 5px;">
            </div>

            <div style="margin-bottom: 15px; text-align: left;">
                <label>Password:</label><br>
                <input type="password" id="password" name="j_password" required style="width: 100%; padding: 5px;">
            </div>

            <button type="submit" style="width: 100%; padding: 10px; background-color: #2d5a27; color: white; border: none;">
                Login
            </button>
        </form>
    </div>

</body>
</html>