package com.example.libis.servlets;

import com.example.libis.data.LoanDAO;
import com.example.libis.data.MemberDAO;
import com.example.libis.model.Book;
import com.example.libis.model.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/myLoans")
public class MyLoansServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getRemoteUser();
        MemberDAO memberDAO = new MemberDAO();
        Member loggedInMember = memberDAO.findByUsername(username);


        if (username == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "You must be logged in to view your loans.");
            return;
        }

        if (loggedInMember == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Member account not found.");
            return;
        }

        // 2. Get their current loans from the database
        LoanDAO loanDAO = new LoanDAO();
        List<Book> myBooks = loanDAO.findOpenLoansByMember(loggedInMember.getMemberId());

        // 3. Put the books in the request
        request.setAttribute("myBooks", myBooks);
        request.setAttribute("loanCount", myBooks.size());

        // 4. Forward to the JSP
        request.getRequestDispatcher("/WEB-INF/views/myLoans.jsp").forward(request, response);
    }
}