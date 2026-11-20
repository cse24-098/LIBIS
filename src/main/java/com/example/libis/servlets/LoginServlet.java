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

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Call the DAO to check the database!
        MemberDAO dao = new MemberDAO();
        boolean isValid = dao.validate(username, password);

        if (isValid) {
            // Success - we create a temporary member to save in the session
            Member loggedInMember = new Member();
            loggedInMember.setEmail(username);
            loggedInMember.setFirstName("Libis");
            loggedInMember.setLastName("User");

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