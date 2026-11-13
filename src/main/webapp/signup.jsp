<%--
  Created by IntelliJ IDEA.
  User: kamog
  Date: 12/11/2026
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Sign Up - LIBIS</title>
  </head>
  <body style="font-family: Arial; background-color: #f0f0f0; padding-top: 50px; text-align: center;">

      <div style="background-color: white; width: 300px; margin: 0 auto; padding: 30px; border: 1px solid #ccc;">
          <h2>Create an Account</h2>

          <!-- Show error if registration fails -->
          <% if (request.getAttribute("error") != null) { %>
              <p style="color: red;"><%= request.getAttribute("error") %></p>
          <% } %>

          <form action="signup" method="post">
              <div style="margin-bottom: 10px; text-align: left;">
                  <label>First Name:</label><br>
                  <input type="text" name="firstName" required style="width: 100%; padding: 5px;">
              </div>
              <div style="margin-bottom: 10px; text-align: left;">
                  <label>Last Name:</label><br>
                  <input type="text" name="lastName" required style="width: 100%; padding: 5px;">
              </div>
              <div style="margin-bottom: 10px; text-align: left;">
                  <label>Email:</label><br>
                  <input type="email" name="email" required style="width: 100%; padding: 5px;">
              </div>
              <div style="margin-bottom: 15px; text-align: left;">
                  <label>Password:</label><br>
                  <input type="password" name="password" required style="width: 100%; padding: 5px;">
              </div>

              <button type="submit" style="width: 100%; padding: 10px; background-color: #2d5a27; color: white; border: none;">Sign Up</button>
          </form>

          <p style="margin-top: 15px; font-size: 14px;">
              Already have an account? <a href="login.jsp">Log in here</a>
          </p>
      </div>

  </body>
  </html>
