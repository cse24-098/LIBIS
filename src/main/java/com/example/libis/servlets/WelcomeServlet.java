package com.example.libis.servlets;

import com.example.libis.data.BookDAO;
import com.example.libis.model.Book;
import com.example.libis.data.MemberDAO;
import com.example.libis.model.Member;
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

        // 1. Call the DAO to get the books from MySQL
        BookDAO bookDAO = new BookDAO();
        List<Book> booksList = bookDAO.getAllBooks();

        // 2. Put them into the Application Scope (so CatalogueServlet can also use them)
        getServletContext().setAttribute("booksList", booksList);

        // Get the username authenticated by Tomcat
        String username = request.getRemoteUser();

        if (username == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "You must log in first.");
            return;
        }

        // Retrieve the member's information from MySQL
        MemberDAO memberDAO = new MemberDAO();
        Member loggedInMember = memberDAO.findByUsername(username);

        if (loggedInMember == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Member account not found.");
            return;
        }

        // Make the member available to welcome.jsp
        request.setAttribute("loggedInMember", loggedInMember);

        // 3. Forward to the protected JSP inside WEB-INF
        request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
    }
}