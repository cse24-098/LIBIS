package com.example.libis.servlets;

import com.example.libis.data.LoanDAO;
import com.example.libis.model.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.example.libis.service.LoanService;

import java.io.IOException;

@WebServlet("/borrow")
public class BorrowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id"); // this gets the book id from the url

        // 1. Get the session and the logged-in member
        HttpSession session = request.getSession(false);
        Member loggedInMember = null;

        if (session != null) {
            loggedInMember = (Member) session.getAttribute("loggedInMember");
        }

        // 2. If they are logged in and there is a book ID, try to borrow
        if (loggedInMember != null && idParam != null) {
            try {
                int bookId = Integer.parseInt(idParam);
                int memberId = loggedInMember.getMemberId(); // Use the real ID from the session!

                // 3. Ask the Service if they are allowed to borrow (Max 3 rule)
                LoanService loanService = new LoanService();

                if (loanService.canBorrow(memberId)) {
                    // They are allowed, so borrow the book
                    LoanDAO loanDAO = new LoanDAO();
                    loanDAO.borrowBook(memberId, bookId);
                } else {
                    // They hit the limit! Send an error message to the welcome page
                    session.setAttribute("errorMessage", "You cannot borrow more than 3 books!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid book ID format.");
            }
        }

        // Send the user back to the welcome page so they see the update
        response.sendRedirect("welcome");
    }
}