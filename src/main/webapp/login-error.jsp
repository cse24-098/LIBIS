<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Failed - LIBIS</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #eef2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .error-container {
            background-color: white;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            text-align: center;
            max-width: 400px;
            width: 100%;
            border-top: 5px solid #c0392b;
        }
        h2 {
            color: #c0392b;
            margin-top: 0;
            font-size: 24px;
            margin-bottom: 15px;
        }
        p {
            color: #555;
            font-size: 15px;
            margin-bottom: 25px;
        }
        .btn-retry {
            background-color: #2d5a27;
            color: white;
            padding: 12px 24px;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
            font-size: 15px;
            display: inline-block;
            transition: background-color 0.2s;
        }
        .btn-retry:hover {
            background-color: #1a3c1a;
        }
    </style>
</head>
<body>

    <div class="error-container">
        <h2>⚠️ Login Failed</h2>
        <p>Invalid username or password.<br>Please check your credentials and try again.</p>
        <a href="login.jsp" class="btn-retry">Return to Login</a>
    </div>

</body>
</html>