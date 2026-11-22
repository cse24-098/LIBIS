<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Books - LIBIS</title>
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
            <h2>Manage Books</h2>
            <a href="welcome" class="back-link">← Back to Catalogue</a>
        </div>

        <div class="form-panel">
            <h3>Add New Book</h3>
            <form action="manageBooks" method="post">
                <input type="text" name="isbn" placeholder="ISBN" required>
                <input type="text" name="title" placeholder="Title" required>
                <input type="text" name="author" placeholder="Author" required>
                <input type="number" name="publishedYear" placeholder="Year" required>
                <input type="text" name="genre" placeholder="Genre">
                <input type="text" name="synopsis" placeholder="Synopsis">
                <button type="submit" class="btn-add">Add Book</button>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="b" items="${bookList}">
                    <tr>
                        <td><strong>${b.title}</strong></td>
                        <td>${b.author}</td>
                        <td class="action-links">
                            <a href="manageBooks?action=edit&id=${b.bookId}" class="link-edit">Edit</a>
                            <a href="manageBooks?action=delete&id=${b.bookId}"
                               onclick="return confirm('Delete this book?');"
                               class="link-delete">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</body>
</html>