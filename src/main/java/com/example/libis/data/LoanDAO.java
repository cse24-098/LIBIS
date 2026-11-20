package com.example.libis.data;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class LoanDAO {

    // This method inserts a new row into the loans table
    public boolean borrowBook(int memberId, int bookId) {
        boolean isSuccess = false;
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            conn = dataSource.getConnection();

            // Insert a new loan. The returned_on is left NULL.
            String sql = "INSERT INTO loans (member_id, book_id, borrowed_on, returned_on) VALUES (?, ?, ?, NULL)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, memberId);
            pst.setInt(2, bookId);
            pst.setDate(3, java.sql.Date.valueOf(LocalDate.now())); // Set today's date

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }

        } catch (Exception e) {
            System.out.println("Error borrowing book: " + e.getMessage());
        } finally {
            if (pst != null) { try { pst.close(); } catch (Exception e) {} }
            if (conn != null) { try { conn.close(); } catch (Exception e) {} }
        }

        return isSuccess;
    }
}