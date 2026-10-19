//a filter is basically like a security guard, it intercepts HTTP requests and responses b4 they reach a servlet or JSP
package com.example.libis.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    //runs once when the filter starts up
    //public void init(FilterConfig) { }

    //it runs every request, what this method does is, id someone tries to open welcome.jsp w/out logging in first, they get bounced to login.jsp
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI(); //checks out what pagethe user is trying to access

        if (path.endsWith("login.jsp") || path.endsWith("login") || path.endsWith(".css")) {
            chain.doFilter(request, response);
            return;
        }

        // 4. Check if they are logged in
        HttpSession session = req.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("loggedInMember") != null);

        if (isLoggedIn) {
            // They are logged in. Let them see the page.
            chain.doFilter(request, response);
        } else {
            // They are NOT logged in. Kick them back to login.jsp
            res.sendRedirect("login.jsp");
        }

    }

    //it runs once when the filter shuts down
    //public void destroy() { }
}
