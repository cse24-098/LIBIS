package com.example.libis.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the session, but don't create a new one if it doesn't exist
        HttpSession session = request.getSession(false);

        // If a session exists, destroy it
        if (session != null) {
            session.invalidate();
        }

        // Send them back to the login page
        response.sendRedirect("login.jsp");
    }
}