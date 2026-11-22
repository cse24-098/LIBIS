<%--
  Created by IntelliJ IDEA.
  User: kamog
  Date: 21/11/2026
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
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
    <title><fmt:message key="books.edit_book" /> - LIBIS</title>
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
            <h2><fmt:message key="books.edit_book" /></h2>
        </div>

        <form action="manageBooks" method="post">
            <!-- Hidden action and ID for updating the book -->
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="bookId" value="${book.bookId}">

            <div class="form-group">
                <label>ISBN:</label>
                <input type="text" name="isbn"
                       value="<c:out value='${book.isbn}' />" required>
            </div>

            <div class="form-group">
                <label><fmt:message key="books.title" />:</label>
                <input type="text" name="title"
                       value="<c:out value='${book.title}' />" required>
            </div>

            <div class="form-group">
                <label><fmt:message key="books.author" />:</label>
                <input type="text" name="author"
                       value="<c:out value='${book.author}' />" required>
            </div>

            <div class="form-group">
                <label><fmt:message key="books.published_year" />:</label>
                <input type="number" name="publishedYear"
                       value="${book.publishedYear}" required>
            </div>

            <div class="form-group">
                <label><fmt:message key="books.genre" />:</label>
                <input type="text" name="genre"
                       value="<c:out value='${book.genre}' />">
            </div>

            <div class="form-group">
                <label><fmt:message key="books.synopsis" />:</label>
                <input type="text" name="synopsis"
                       value="<c:out value='${book.synopsis}' />">
            </div>

            <div class="actions">
                <button type="submit" class="btn-save">
                    <fmt:message key="books.save_changes" />
                </button>

                <a href="manageBooks" class="btn-cancel">
                    <fmt:message key="books.cancel" />
                </a>
            </div>
        </form>
    </div>
</body>

</html>