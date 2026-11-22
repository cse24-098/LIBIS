package com.example.libis.data;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import com.example.libis.model.Book;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    // 1. Get books the member currently has out
    public List<Book> findOpenLoansByMember(int memberId) {
        List<Book> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            conn = dataSource.getConnection();

            // Join loans with books to get the book details
            String sql = "SELECT b.* FROM books b JOIN loans l ON b.book_id = l.book_id WHERE l.member_id = ? AND l.returned_on IS NULL";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, memberId);
            rs = pst.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                list.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) {} }
            if (pst != null) { try { pst.close(); } catch (Exception e) {} }
            if (conn != null) { try { conn.close(); } catch (Exception e) {} }
        }
        return list;
    }

    // 2. Mark a book as returned (sets the date)
    public boolean returnBook(int memberId, int bookId) {
        boolean isSuccess = false;
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            conn = dataSource.getConnection();

            // Set returned_on to today's date
            String sql = "UPDATE loans SET returned_on = ? WHERE member_id = ? AND book_id = ? AND returned_on IS NULL";
            pst = conn.prepareStatement(sql);

            // Get today's date
            pst.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
            pst.setInt(2, memberId);
            pst.setInt(3, bookId);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pst != null) { try { pst.close(); } catch (Exception e) {} }
            if (conn != null) { try { conn.close(); } catch (Exception e) {} }
        }
        return isSuccess;
    }

    public int countOpenLoans(int memberId) {
        int count = 0;
        try {
            javax.naming.Context context = new javax.naming.InitialContext();
            javax.sql.DataSource ds = (javax.sql.DataSource) context.lookup("java:comp/env/jdbc/libis_db");

            try (java.sql.Connection conn = ds.getConnection();
                 java.sql.PreparedStatement pst = conn.prepareStatement("SELECT COUNT(*) FROM loans WHERE member_id = ? AND returned_on IS NULL")) {

                pst.setInt(1, memberId);
                try (java.sql.ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        count = rs.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Unable to count active loans", e);
        }
        return count;
    }
}