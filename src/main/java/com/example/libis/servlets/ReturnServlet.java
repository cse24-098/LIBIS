package com.example.libis.servlets;

import com.example.libis.data.LoanDAO;
import com.example.libis.model.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.libis.data.MemberDAO;
import java.io.IOException;

@WebServlet("/return")
public class ReturnServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getRemoteUser();

        if (username == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "You must be logged in to return books.");
            return;
        }

        MemberDAO memberDAO = new MemberDAO();
        Member loggedInMember = memberDAO.findByUsername(username);

        if (loggedInMember == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Member account not found.");
            return;
        }
        if (loggedInMember != null) {
            String bookIdStr = request.getParameter("bookId");
            if (bookIdStr != null) {
                int bookId = Integer.parseInt(bookIdStr);

                LoanDAO loanDAO = new LoanDAO();
                loanDAO.returnBook(loggedInMember.getMemberId(), bookId);
            }
        }

        // Send them back to the My Loans page to see the updated list
        response.sendRedirect("myLoans");
    }
}