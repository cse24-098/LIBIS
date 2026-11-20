package com.example.libis.servlets;

import com.example.libis.data.LoanDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/borrow")
public class BorrowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id"); //this gets the book id from the url

        if (idParam != null) {
            try {
                int bookId = Integer.parseInt(idParam);

                int memberId = 1;

                // Call the new LoanDAO!
                LoanDAO loanDAO = new LoanDAO();
                loanDAO.borrowBook(memberId, bookId);

            } catch (NumberFormatException e) {
                System.out.println("Invalid book ID format.");
            }
        }

        //this sends the user back to the welcome page so they see the update
        response.sendRedirect("welcome");
    }

}
