package com.example.libis.data;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.libis.model.Member;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    // 1. Login Validation
    public boolean validate(String username, String pass) {
        boolean status = false;
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            try (Connection conn = ds.getConnection();
                 PreparedStatement pst = conn.prepareStatement("SELECT * FROM members WHERE username=? AND password=?")) {
                pst.setString(1, username);
                pst.setString(2, pass);
                try (ResultSet rs = pst.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (Exception e) {
            System.out.println("Login validation error: " + e.getMessage());
        }
        return status;
    }

    // 2. GET ALL MEMBERS (Now retrieves member_number)
    public List<Member> findAll() {
        List<Member> list = new ArrayList<>();
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            try (Connection conn = ds.getConnection();
                 PreparedStatement pst = conn.prepareStatement("SELECT * FROM members");
                 ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Member m = new Member();
                    m.setMemberId(rs.getInt("member_id"));
                    m.setMemberNumber(rs.getString("member_number")); // Added
                    m.setFullName(rs.getString("full_name"));
                    m.setEmail(rs.getString("email"));
                    m.setUsername(rs.getString("username"));
                    m.setRole(rs.getString("role"));
                    list.add(m);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 3. CREATE A MEMBER (Uses actual member_number and password from the object)
    public boolean create(Member m) {
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            String sql = "INSERT INTO members (member_number, full_name, email, username, password, role) VALUES (?, ?, ?, ?, ?, 'member')";
            try (Connection conn = ds.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, m.getMemberNumber());
                pst.setString(2, m.getFullName());
                pst.setString(3, m.getEmail());
                pst.setString(4, m.getUsername());
                pst.setString(5, m.getPassword());
                return pst.executeUpdate() > 0;
            }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 4. DELETE A MEMBER
    public boolean delete(int memberId) {
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            try (Connection conn = ds.getConnection();
                 PreparedStatement pst = conn.prepareStatement("DELETE FROM members WHERE member_id = ?")) {
                pst.setInt(1, memberId);
                return pst.executeUpdate() > 0;
            }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 5. FIND BY USERNAME (Now retrieves member_number)
    public Member findByUsername(String username) {
        Member member = null;
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            try (Connection conn = ds.getConnection();
                 PreparedStatement pst = conn.prepareStatement("SELECT * FROM members WHERE username = ?")) {
                pst.setString(1, username);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        member = new Member();
                        member.setMemberId(rs.getInt("member_id"));
                        member.setMemberNumber(rs.getString("member_number")); // Added
                        member.setFullName(rs.getString("full_name"));
                        member.setEmail(rs.getString("email"));
                        member.setUsername(rs.getString("username"));
                        member.setPassword(rs.getString("password"));
                        member.setRole(rs.getString("role"));
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return member;
    }

    // 6. FIND BY ID (Now retrieves member_number)
    public Member findById(int memberId) {
        Member member = null;
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            try (Connection conn = ds.getConnection();
                 PreparedStatement pst = conn.prepareStatement("SELECT * FROM members WHERE member_id = ?")) {
                pst.setInt(1, memberId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        member = new Member();
                        member.setMemberId(rs.getInt("member_id"));
                        member.setMemberNumber(rs.getString("member_number")); // Added
                        member.setFullName(rs.getString("full_name"));
                        member.setEmail(rs.getString("email"));
                        member.setUsername(rs.getString("username"));
                        member.setRole(rs.getString("role"));
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return member;
    }

    // 7. UPDATE MEMBER (Supports updating member number and optionally password)
    public boolean update(Member m) {
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");

            // Check if we need to update the password
            boolean updatePassword = (m.getPassword() != null && !m.getPassword().trim().isEmpty());

            String sql;
            if (updatePassword) {
                sql = "UPDATE members SET member_number = ?, full_name = ?, email = ?, username = ?, password = ? WHERE member_id = ?";
            } else {
                sql = "UPDATE members SET member_number = ?, full_name = ?, email = ?, username = ? WHERE member_id = ?";
            }

            try (Connection conn = ds.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, m.getMemberNumber());
                pst.setString(2, m.getFullName());
                pst.setString(3, m.getEmail());
                pst.setString(4, m.getUsername());

                if (updatePassword) {
                    pst.setString(5, m.getPassword());
                    pst.setInt(6, m.getMemberId());
                } else {
                    pst.setInt(5, m.getMemberId());
                }

                return pst.executeUpdate() > 0;
            }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 8. NEW: Find by Member Number (For duplicate checks)
    public Member findByMemberNumber(String memberNumber) {
        Member member = null;
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            try (Connection conn = ds.getConnection();
                 PreparedStatement pst = conn.prepareStatement("SELECT * FROM members WHERE member_number = ?")) {
                pst.setString(1, memberNumber);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        member = new Member();
                        member.setMemberId(rs.getInt("member_id"));
                        member.setMemberNumber(rs.getString("member_number"));
                        member.setFullName(rs.getString("full_name"));
                        member.setEmail(rs.getString("email"));
                        member.setUsername(rs.getString("username"));
                        member.setRole(rs.getString("role"));
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return member;
    }

    // 9. NEW: Find by Email (For duplicate checks)
    public Member findByEmail(String email) {
        Member member = null;
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            try (Connection conn = ds.getConnection();
                 PreparedStatement pst = conn.prepareStatement("SELECT * FROM members WHERE email = ?")) {
                pst.setString(1, email);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        member = new Member();
                        member.setMemberId(rs.getInt("member_id"));
                        member.setMemberNumber(rs.getString("member_number"));
                        member.setFullName(rs.getString("full_name"));
                        member.setEmail(rs.getString("email"));
                        member.setUsername(rs.getString("username"));
                        member.setRole(rs.getString("role"));
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return member;
    }

    // 10. NEW: Search by Name or Member Number
    public List<Member> search(String searchTerm) {
        List<Member> list = new ArrayList<>();
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            // Use LIKE with wildcards for partial matches on name or member number
            String sql = "SELECT * FROM members WHERE full_name LIKE ? OR member_number LIKE ?";
            try (Connection conn = ds.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                String wildcardSearch = "%" + searchTerm + "%";
                pst.setString(1, wildcardSearch);
                pst.setString(2, wildcardSearch);

                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Member m = new Member();
                        m.setMemberId(rs.getInt("member_id"));
                        m.setMemberNumber(rs.getString("member_number"));
                        m.setFullName(rs.getString("full_name"));
                        m.setEmail(rs.getString("email"));
                        m.setUsername(rs.getString("username"));
                        m.setRole(rs.getString("role"));
                        list.add(m);
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}