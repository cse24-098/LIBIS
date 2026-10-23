package com.example.libis.servlets;

import com.example.libis.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/search")
public class CatalogueServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("query");

        @SuppressWarnings("unchecked")
        List<Book> allBooks = (List<Book>) getServletContext().getAttribute("books");

        List<Book> results = new ArrayList<>();

        if (query ==null || query.trim().isEmpty()) {
            results = allBooks;
        } else {
            String search = query.trim().toLowerCase();

            for (Book b : allBooks) {
                if (b.getTitle().toLowerCase().contains(search) ||
                        b.getAuthor().toLowerCase().contains(search) ||
                        b.getGenre().toLowerCase().contains(search)) {
                    results.add(b);
                }
            }
        }

        request.setAttribute("searchQuery", query);
        request.setAttribute("results", results);
        request.setAttribute("resultCount", results.size());

        request.getRequestDispatcher("search.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
