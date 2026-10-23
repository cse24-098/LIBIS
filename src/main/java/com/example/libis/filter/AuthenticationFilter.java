package com.example.libis.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();

        // 1. Allow login page, login servlet, and static assets (css, js)
        if (path.endsWith("login.jsp") || path.endsWith("login") || path.endsWith(".css") || path.endsWith(".js")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Check session
        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("loggedInMember") != null);

        // 3. Let through or redirect
        if (isLoggedIn) {
            chain.doFilter(request, response);
        } else {
            // Redirect to /login as requested by the paper
            res.sendRedirect("login");
        }
    }
}