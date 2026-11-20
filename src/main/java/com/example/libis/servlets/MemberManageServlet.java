package com.example.libis.servlets;

import com.example.libis.model.Member;
import com.example.libis.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/manageMembers")
public class MemberManageServlet extends HttpServlet {

    private MemberService memberService = new MemberService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            memberService.deleteMember(id);
            response.sendRedirect("manageMembers"); // Go back to list
        } else {
            // Default: Show List
            List<Member> members = memberService.getAllMembers();
            request.setAttribute("memberList", members);
            request.getRequestDispatcher("/WEB-INF/views/listMembers.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle the "Create Member" form submission
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String username = request.getParameter("username");

        Member newMember = new Member();
        newMember.setFullName(fullName);
        newMember.setEmail(email);
        newMember.setUsername(username);

        memberService.createMember(newMember);
        response.sendRedirect("manageMembers"); // Redirect back to the list
    }
}