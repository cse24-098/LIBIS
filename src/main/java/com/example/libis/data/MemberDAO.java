package com.example.libis.data;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

    //this parts reads the database during login
 public boolean validate(String email, String pass) {
        System.out.println("Validate called");

        boolean status = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {


            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");

            conn = dataSource.getConnection();

            pst = conn.prepareStatement("select * from members where email=? and password=?");
            pst.setString(1, email);
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

    //this is for writing into the database, like adding a new member basically
    public boolean register(String firstName, String lastName, String email, String pass) {
        boolean isSuccess = false;
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            conn = dataSource.getConnection();

            String sql = "INSERT INTO members (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setString(3, email);
            pst.setString(4, pass);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("Error registering member: " + e.getMessage());
        } finally {
            if (pst != null) { try { pst.close(); } catch (Exception e) {} }
            if (conn != null) { try { conn.close(); } catch (Exception e) {} }
        }

        return isSuccess;
    }
}
