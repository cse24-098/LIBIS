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
import java.util.ArrayList;

@WebServlet("/manageMembers")
public class MemberManageServlet extends HttpServlet {

    private MemberService memberService = new MemberService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            // --- HANDLE EDIT FORM LOAD ---
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Member member = memberService.getMemberById(id);

                if (member == null) {
                    response.sendRedirect(request.getContextPath() + "/manageMembers");
                    return;
                }

                request.setAttribute("member", member);
                request.getRequestDispatcher("/WEB-INF/views/editMember.jsp").forward(request, response);

            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/manageMembers");
            }

        } else {
            // --- HANDLE LIST & SEARCH ---
            String searchTerm = request.getParameter("search");
            List<Member> members;

            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                members = memberService.searchMembers(searchTerm);
            } else {
                members = memberService.getAllMembers();
            }

            request.setAttribute("memberList", members);
            request.getRequestDispatcher("/WEB-INF/views/listMembers.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        List<String> errors = new ArrayList<>();

        if ("delete".equals(action)) {
            // --- HANDLE DELETE (VIA POST) ---
            try {
                int targetId = Integer.parseInt(request.getParameter("id"));

                // 1. Get the authenticated username securely
                String username = request.getRemoteUser();
                Member loggedInUser = null;

                if (username != null) {
                    loggedInUser = memberService.getMemberByUsername(username);
                }

                // 2. Check if we actually found the logged-in user
                if (loggedInUser == null) {
                    errors.add("Security error: Could not verify your identity.");
                } else {
                    // 3. Proceed with deletion
                    errors = memberService.deleteMember(targetId, loggedInUser.getMemberId());
                }

                // 4. Handle the result
                if (errors.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/manageMembers");
                } else {
                    request.setAttribute("errorList", errors);
                    request.setAttribute("memberList", memberService.getAllMembers());
                    request.getRequestDispatcher("/WEB-INF/views/listMembers.jsp").forward(request, response);
                }

            } catch (NumberFormatException e) {
                // Handle malformed ID
                List<String> formatErrors = new ArrayList<>();
                formatErrors.add("Invalid member ID format.");
                request.setAttribute("errorList", formatErrors);
                request.setAttribute("memberList", memberService.getAllMembers());
                request.getRequestDispatcher("/WEB-INF/views/listMembers.jsp").forward(request, response);
            }

        } else if ("update".equals(action)) {
            // --- HANDLE UPDATE ---
            try {
                Member m = new Member();
                m.setMemberId(Integer.parseInt(request.getParameter("memberId")));
                m.setMemberNumber(request.getParameter("memberNumber"));
                m.setFullName(request.getParameter("fullName"));
                m.setEmail(request.getParameter("email"));
                m.setUsername(request.getParameter("username"));
                m.setPassword(request.getParameter("password")); // Optional

                errors = memberService.updateMember(m);

                if (errors.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/manageMembers");
                } else {
                    request.setAttribute("errorList", errors);
                    request.setAttribute("member", m); // Preserve edited values
                    request.getRequestDispatcher("/WEB-INF/views/editMember.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                // Handle malformed update ID
                List<String> formatErrors = new ArrayList<>();
                formatErrors.add("Invalid member ID format.");
                request.setAttribute("errorList", formatErrors);
                request.setAttribute("memberList", memberService.getAllMembers());
                request.getRequestDispatcher("/WEB-INF/views/listMembers.jsp").forward(request, response);
            }

        } else if ("create".equals(action)) {
            // --- HANDLE CREATE ---
            Member m = new Member();
            m.setMemberNumber(request.getParameter("memberNumber"));
            m.setFullName(request.getParameter("fullName"));
            m.setEmail(request.getParameter("email"));
            m.setUsername(request.getParameter("username"));
            m.setPassword(request.getParameter("password"));

            errors = memberService.createMember(m);

            if (errors.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/manageMembers");
            } else {
                request.setAttribute("errorList", errors);
                m.setPassword(null); // Clear password for security
                request.setAttribute("member", m); // Preserve entered values
                request.setAttribute("memberList", memberService.getAllMembers());
                request.getRequestDispatcher("/WEB-INF/views/listMembers.jsp").forward(request, response);
            }
        } else {
            // Unknown action
            response.sendRedirect(request.getContextPath() + "/manageMembers");
        }
    }
}