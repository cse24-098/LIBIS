package com.example.libis.service;

import com.example.libis.data.MemberDAO;
import com.example.libis.model.Member;
import java.util.ArrayList;
import java.util.List;

public class MemberService {
    private MemberDAO memberDAO = new MemberDAO();

    public List<Member> getAllMembers() {
        return memberDAO.findAll();
    }

    public Member getMemberById(int id) {
        return memberDAO.findById(id);
    }

    public List<String> createMember(Member m) {
        List<String> errors = new ArrayList<>();

        // checks all five required fields
        if (m.getMemberNumber() == null || m.getMemberNumber().trim().isEmpty() ||
                m.getFullName() == null || m.getFullName().trim().isEmpty() ||
                m.getEmail() == null || m.getEmail().trim().isEmpty() ||
                m.getUsername() == null || m.getUsername().trim().isEmpty() ||
                m.getPassword() == null || m.getPassword().trim().isEmpty()) {
            errors.add("All fields are required.");
            return errors; // Stop here if fields are empty
        }

        m.setMemberNumber(m.getMemberNumber().trim());
        m.setFullName(m.getFullName().trim());
        m.setEmail(m.getEmail().trim());
        m.setUsername(m.getUsername().trim());

        // for email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!m.getEmail().matches(emailRegex)) {
            errors.add("Please enter a valid email address (e.g., name@domain.com).");
        }

        // check for the duplicates
        if (memberDAO.findByMemberNumber(m.getMemberNumber()) != null) {
            errors.add("Member Number is already in use.");
        }
        if (memberDAO.findByEmail(m.getEmail()) != null) {
            errors.add("Email is already registered.");
        }
        if (memberDAO.findByUsername(m.getUsername()) != null) {
            errors.add("Username is already taken.");
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        // calls the DAO only if validation succeeds
        boolean isCreated = memberDAO.create(m);
        if (!isCreated) {
            errors.add("Database error: Could not create member.");
        }

        return errors;
    }

    public List<String> deleteMember(int targetMemberId, int loggedInLibrarianId) {
        List<String> errors = new ArrayList<>();

        // Rule: A librarian cannot delete their own account
        if (targetMemberId == loggedInLibrarianId) {
            errors.add("You cannot delete your own account while signed in.");
            return errors;
        }

        // Attempt the deletion
        boolean isDeleted = memberDAO.delete(targetMemberId);

        if (!isDeleted) {
            errors.add("Failed to delete member. The account may not exist.");
        }

        return errors;
    }

    public List<String> updateMember(Member m) {
        List<String> errors = new ArrayList<>();

        // checks required fields
        if (m.getMemberNumber() == null || m.getMemberNumber().trim().isEmpty() ||
                m.getFullName() == null || m.getFullName().trim().isEmpty() ||
                m.getEmail() == null || m.getEmail().trim().isEmpty() ||
                m.getUsername() == null || m.getUsername().trim().isEmpty()) {
            errors.add("Member Number, Full Name, Email, and Username are required.");
            return errors;
        }

        m.setMemberNumber(m.getMemberNumber().trim());
        m.setFullName(m.getFullName().trim());
        m.setEmail(m.getEmail().trim());
        m.setUsername(m.getUsername().trim());

        // validates the email format
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!m.getEmail().matches(emailRegex)) {
            errors.add("Please enter a valid email address.");
        }

        // a duplicate check

        Member existingByNumber = memberDAO.findByMemberNumber(m.getMemberNumber());
        if (existingByNumber != null && existingByNumber.getMemberId() != m.getMemberId()) {
            errors.add("Member Number is already in use by another member.");
        }

        Member existingByEmail = memberDAO.findByEmail(m.getEmail());
        if (existingByEmail != null && existingByEmail.getMemberId() != m.getMemberId()) {
            errors.add("Email is already registered to another member.");
        }

        Member existingByUsername = memberDAO.findByUsername(m.getUsername());
        if (existingByUsername != null && existingByUsername.getMemberId() != m.getMemberId()) {
            errors.add("Username is already taken by another member.");
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        // this calls DAO to update
        boolean isUpdated = memberDAO.update(m);
        if (!isUpdated) {
            errors.add("Database error: Could not update member.");
        }

        return errors;
    }

    public List<Member> searchMembers(String searchTerm) {
        // If the search term is null or empty, just return all members
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return memberDAO.findAll();
        }

        // Otherwise, pass the search term to the DAO
        return memberDAO.search(searchTerm.trim());
    }

    public Member getMemberByUsername(String username) {
        return memberDAO.findByUsername(username);
    }
}