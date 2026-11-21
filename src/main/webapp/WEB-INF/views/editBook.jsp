<%--
  Created by IntelliJ IDEA.
  User: kamog
  Date: 21/11/2026
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Edit Book</title></head>
<body style="font-family: Arial; padding: 20px; background-color: #f0f0f0;">
    <div style="background: white; max-width: 500px; margin: 0 auto; padding: 20px; border: 1px solid #ccc;">
        <h2>Edit Book</h2>

        <form action="manageBooks" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="bookId" value="${book.bookId}">

            <p>ISBN:</p>
            <input type="text" name="isbn" value="${book.isbn}" required style="width: 100%;">

            <p>Title:</p>
            <input type="text" name="title" value="${book.title}" required style="width: 100%;">

            <p>Author:</p>
            <input type="text" name="author" value="${book.author}" required style="width: 100%;">

            <p>Published Year:</p>
            <input type="number" name="publishedYear" value="${book.publishedYear}" required style="width: 100%;">

            <p>Genre:</p>
            <input type="text" name="genre" value="${book.genre}" style="width: 100%;">

            <br><br>
            <button type="submit" style="background: #2d5a27; color: white; padding: 10px 20px; border: none;">Save Changes</button>
            <a href="manageBooks" style="margin-left: 10px;">Cancel</a>
        </form>
    </div>
</body>
</html>
