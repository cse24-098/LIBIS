package com.example.libis.servlets;

import com.example.libis.model.Book;
import com.example.libis.model.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private List<Member> membersList;
    private List<Book> booksList;

    @Override
    public void init() throws ServletException {
        membersList = new ArrayList<>();
        membersList.add(new Member(1, "Kamogelo", "Morerwa", "kamogelo@gmail.com", "pass123"));
        membersList.add(new Member(2, "Pako", "Daniel", "pakodaniel@gmail.com", "pako456"));
        membersList.add(new Member(3, "Kago", "Motladiile", "kagomotladiile@gmail.com", "kago789"));
        membersList.add(new Member(4, "Jennie", "Tebatso", "jennietebatso@gmail.com", "jennie321"));
        membersList.add(new Member(5, "Eunice", "Morerwa", "eunice@libis.ac.bw", "eunice654"));

        booksList = new ArrayList<>();
        booksList.add(new Book(1, "Understanding Mathematics", "T. M. Mmusi", "Mathematics", "A basic math book.", true));
        booksList.add(new Book(2, "Physical Science", "K. P. Sebina", "Science", "Intro to physics.", false));
        booksList.add(new Book(3, "History of Botswana", "M. L. Mokgosi", "History", "Botswana history.", true));
        booksList.add(new Book(4, "English Grammar", "S. N. Kgosi", "English", "Grammar guide.", true));
        booksList.add(new Book(5, "Computer Studies", "T. D. Ntwaagae", "ICT", "Basic computers.", false));
        booksList.add(new Book(6, "Geography", "R. M. Matlho", "Geography", "Southern Africa.", true));
        booksList.add(new Book(7, "Biology", "M. P. Ketshabile", "Biology", "Life sciences.", true));
        booksList.add(new Book(8, "Business Studies", "L. N. Mokgethi", "Business", "Intro to business.", false));
        booksList.add(new Book(9, "Setswana Literature", "B. T. Mothibedi", "Setswana", "Setswana poems.", true));
        booksList.add(new Book(10, "Agricultural Science", "K. M. Tshosa", "Agriculture", "Farming basics.", true));

        getServletContext().setAttribute("membersList", membersList);
        getServletContext().setAttribute("booksList", booksList);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Member foundMember = null;
        for (Member member : membersList) {
            if (member.getEmail().equals(email) && member.getPassword().equals(password)) {
                foundMember = member;
                break;
            }
        }

        if (foundMember != null) {
            // SUCCESS: Save to session and go to welcome
            HttpSession session = request.getSession();
            session.setAttribute("loggedInMember", foundMember);
            response.sendRedirect("welcome");
        } else {
            // FAILURE: Show error and stay on login page
            request.setAttribute("error", "Invalid email or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}