package com.example.libis.servlets;

import com.example.libis.data.LoanDAO;
import com.example.libis.model.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/return")
public class ReturnServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");

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