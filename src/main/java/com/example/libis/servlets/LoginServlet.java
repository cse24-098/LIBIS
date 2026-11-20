package com.example.libis.servlets;

import com.example.libis.data.MemberDAO;
import com.example.libis.model.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Read username (not email!) from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 2. Check against the database
        MemberDAO dao = new MemberDAO();
        boolean isValid = dao.validate(username, password);

        if (isValid) {
            // 3. Get the full Member object from the database
            Member loggedInMember = dao.findByUsername(username);

            HttpSession session = request.getSession();
            session.setAttribute("loggedInMember", loggedInMember);

            response.sendRedirect("welcome");
        } else {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}