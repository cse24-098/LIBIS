<%--
  Created by IntelliJ IDEA.
  User: kamog
  Date: 21/11/2026
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head><title>Manage Books</title></head>
<body style="font-family: Arial; background-color: #f0f0f0; padding: 20px;">
    <div style="background: white; max-width: 900px; margin: 0 auto; padding: 20px; border: 1px solid #ccc;">
        <div style="display: flex; justify-content: space-between; border-bottom: 2px solid #2d5a27; padding-bottom: 10px;">
            <h2>Manage Books</h2>
            <a href="welcome">Back to Catalogue</a>
        </div>

        <div style="background-color: #e8f5e9; padding: 15px; margin-top: 20px;">
            <h3>Add New Book</h3>
            <form action="manageBooks" method="post">
                <input type="text" name="isbn" placeholder="ISBN" required>
                <input type="text" name="title" placeholder="Title" required>
                <input type="text" name="author" placeholder="Author" required>
                <input type="number" name="publishedYear" placeholder="Year" required>
                <input type="text" name="genre" placeholder="Genre">
                <input type="text" name="synopsis" placeholder="Synopsis">
                <button type="submit" style="background-color: #2d5a27; color: white; padding: 5px 10px;">Add Book</button>
            </form>
        </div>

        <table style="width: 100%; border-collapse: collapse; margin-top: 20px;">
            <tr style="background-color: #2d5a27; color: white;">
                <th style="padding: 10px; text-align: left;">Title</th>
                <th style="padding: 10px; text-align: left;">Author</th>
                <th style="padding: 10px; text-align: left;">Action</th>
            </tr>
            <c:forEach var="b" items="${bookList}">
                <tr style="border-bottom: 1px solid #ddd;">
                    <td style="padding: 10px;">${b.title}</td>
                    <td style="padding: 10px;">${b.author}</td>
                    <td style="padding: 10px;">
                        <a href="manageBooks?action=edit&id=${b.bookId}" style="color: blue;">Edit</a> |
                        <a href="manageBooks?action=delete&id=${b.bookId}"
                           onclick="return confirm('Delete this book?');" style="color: red;">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
