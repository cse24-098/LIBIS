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

    //this parts reads the database during login
 public boolean validate(String username, String pass) {
        System.out.println("Validate called");

        boolean status = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {


            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");

            conn = dataSource.getConnection();

            pst = conn.prepareStatement("select * from members where username=? and password=?");
            pst.setString(1, username);
            pst.setString(2, pass);

            rs = pst.executeQuery();


            return rs.next();


        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }

    // 1. GET ALL MEMBERS
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
                    m.setFullName(rs.getString("full_name"));
                    m.setEmail(rs.getString("email"));
                    m.setUsername(rs.getString("username"));
                    list.add(m);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. CREATE A MEMBER
    public boolean create(Member m) {
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            String sql = "INSERT INTO members (member_number, full_name, email, username, password, role) VALUES (?, ?, ?, ?, ?, 'member')";
            try (Connection conn = ds.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, "MEM" + (int)(Math.random() * 10000)); // Generate random member number
                pst.setString(2, m.getFullName());
                pst.setString(3, m.getEmail());
                pst.setString(4, m.getUsername());
                pst.setString(5, "pass123"); // Default password for new members
                return pst.executeUpdate() > 0;
            }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 3. DELETE A MEMBER (Cascading delete handles loans automatically in MySQL!)
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

    // This method fetches the full Member object so we can save it in the session
    public Member findByUsername(String username) {
        Member member = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            conn = dataSource.getConnection();

            String sql = "SELECT * FROM members WHERE username = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);

            rs = pst.executeQuery();

            if (rs.next()) {
                member = new Member();
                member.setMemberId(rs.getInt("member_id"));
                member.setFullName(rs.getString("full_name"));
                member.setEmail(rs.getString("email"));
                member.setUsername(rs.getString("username"));
                member.setPassword(rs.getString("password"));
                member.setRole(rs.getString("role"));
            }

        } catch (Exception e) {
            System.out.println("Error finding member: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) {} }
            if (pst != null) { try { pst.close(); } catch (Exception e) {} }
            if (conn != null) { try { conn.close(); } catch (Exception e) {} }
        }

        return member;
    }

    // 1. Find a single member by their ID (For loading the Edit form)
    public Member findById(int memberId) {
        Member member = null;
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement("SELECT * FROM members WHERE member_id = ?")) {
                pst.setInt(1, memberId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        member = new Member();
                        member.setMemberId(rs.getInt("member_id"));
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

    // 2. Update an existing member
    public boolean update(Member m) {
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            String sql = "UPDATE members SET full_name = ?, email = ?, username = ? WHERE member_id = ?";
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, m.getFullName());
                pst.setString(2, m.getEmail());
                pst.setString(3, m.getUsername());
                pst.setInt(4, m.getMemberId());
                return pst.executeUpdate() > 0;
            }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

}
