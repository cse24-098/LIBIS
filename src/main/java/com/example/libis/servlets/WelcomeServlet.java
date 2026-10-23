package com.example.libis.servlets;

import com.example.libis.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the books from the in-memory list we created in LoginServlet
        List<Book> booksList = (List<Book>) getServletContext().getAttribute("booksList");

        // Put it in the request so welcome.jsp can read it
        request.setAttribute("booksList", booksList);

        // Forward to the JSP inside WEB-INF
        request.getRequestDispatcher("/WEB-INF/welcome.jsp").forward(request, response);
    }
}