package com.example.libis.servlets;

import com.example.libis.model.Book;
import com.example.libis.service.BookService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/manageBooks")
public class BookManageServlet extends HttpServlet {

    private BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            bookService.deleteBook(id);
            response.sendRedirect("manageBooks");
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Book book = bookService.getBookById(id);
            request.setAttribute("book", book);
            request.getRequestDispatcher("/WEB-INF/views/editBook.jsp").forward(request, response);
        } else {
            // 1. Get the list from the database
            List<Book> books = bookService.getAllBooks();

            // 2. Put the list into the request under the name "bookList"
            request.setAttribute("bookList", books);

            // 3. Forward to the JSP
            request.getRequestDispatcher("/WEB-INF/views/listBooks.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        Book b = new Book();
        b.setIsbn(request.getParameter("isbn"));
        b.setTitle(request.getParameter("title"));
        b.setAuthor(request.getParameter("author"));
        b.setPublishedYear(Integer.parseInt(request.getParameter("publishedYear")));
        b.setGenre(request.getParameter("genre"));
        b.setSynopsis(request.getParameter("synopsis"));

        if ("update".equals(action)) {
            b.setBookId(Integer.parseInt(request.getParameter("bookId")));
            bookService.updateBook(b);
        } else {
            bookService.createBook(b);
        }

        response.sendRedirect("manageBooks");
    }
}