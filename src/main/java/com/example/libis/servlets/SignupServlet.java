package com.example.libis.servlets;

import com.example.libis.data.MemberDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends  HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        //this reads from the HTML form
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // this calls the DAO to save it to the database
        MemberDAO memberDAO = new MemberDAO();
        boolean success = memberDAO.register(firstName, lastName, email, password);

        if (success) {
            request.setAttribute("successMessage", "Account created successfully! Please log in."); //this would send them to the login page
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Account already exists!");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);//this is when the user failed to signup probably becaise the email alread exists
        }
    }

    @Override
    protected  void  doGet(HttpServletRequest request,  HttpServletResponse response)
        throws ServletException, IOException {
        request.getRequestDispatcher("/signup.jsp").forward(request, response);
    }
}
