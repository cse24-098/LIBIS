package com.example.libis.data;

import com.example.libis.model.Book;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> getAllBooks() {
        List<Book> booksList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // 1. Connect using your context.xml connection pool
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            conn = dataSource.getConnection();

            // 2. Get all books from the database
            String sql = "SELECT b.*, " +
                    "(CASE WHEN l.loan_id IS NULL THEN 1 ELSE 0 END) as is_available " +
                    "FROM books b " +
                    "LEFT JOIN loans l ON b.book_id = l.book_id AND l.returned_on IS NULL";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            // 3. Loop through the results and create Book objects
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setIsbn(rs.getString("isbn"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublishedYear(rs.getInt("published_year"));
                book.setGenre(rs.getString("genre"));
                book.setSynopsis(rs.getString("synopsis"));

                //sets the calculated availability from left join
                book.setAvailable(rs.getInt("is_available") == 1);

                booksList.add(book);

            }

            System.out.println("Total books fetched from DB: " + booksList.size());

        } catch (Exception e) {
            System.out.println("ERROR IN BOOKDAO:");
            e.printStackTrace();
        } finally {
            // Close everything
            if (rs != null) { try { rs.close(); } catch (Exception e) {} }
            if (pst != null) { try { pst.close(); } catch (Exception e) {} }
            if (conn != null) { try { conn.close(); } catch (Exception e) {} }
        }

        System.out.println("DEBUG: Books being sent to JSP: " + booksList.size());
        return booksList;
    }

    // 1. CREATE
    public boolean create(Book b) {
        try {
            javax.naming.Context context = new javax.naming.InitialContext();
            javax.sql.DataSource ds = (javax.sql.DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            String sql = "INSERT INTO books (isbn, title, author, published_year, genre, synopsis) VALUES (?, ?, ?, ?, ?, ?)";
            try (java.sql.Connection conn = ds.getConnection();
                 java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, b.getIsbn());
                pst.setString(2, b.getTitle());
                pst.setString(3, b.getAuthor());
                pst.setInt(4, b.getPublishedYear());
                pst.setString(5, b.getGenre());
                pst.setString(6, b.getSynopsis());
                return pst.executeUpdate() > 0;
            }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 2. DELETE (Will fail if the book is currently on loan due to the rules we will add later)
    public boolean delete(int bookId) {
        try {
            javax.naming.Context context = new javax.naming.InitialContext();
            javax.sql.DataSource ds = (javax.sql.DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            try (java.sql.Connection conn = ds.getConnection();
                 java.sql.PreparedStatement pst = conn.prepareStatement("DELETE FROM books WHERE book_id = ?")) {
                pst.setInt(1, bookId);
                return pst.executeUpdate() > 0;
            }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 3. FIND BY ID (For Editing)
    public Book findById(int bookId) {
        Book book = null;
        try {
            javax.naming.Context context = new javax.naming.InitialContext();
            javax.sql.DataSource ds = (javax.sql.DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            try (java.sql.Connection conn = ds.getConnection();
                 java.sql.PreparedStatement pst = conn.prepareStatement("SELECT * FROM books WHERE book_id = ?")) {
                pst.setInt(1, bookId);
                try (java.sql.ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        book = new Book();
                        book.setBookId(rs.getInt("book_id"));
                        book.setIsbn(rs.getString("isbn"));
                        book.setTitle(rs.getString("title"));
                        book.setAuthor(rs.getString("author"));
                        book.setPublishedYear(rs.getInt("published_year"));
                        book.setGenre(rs.getString("genre"));
                        book.setSynopsis(rs.getString("synopsis"));
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return book;
    }

    // 4. UPDATE
    public boolean update(Book b) {
        try {
            javax.naming.Context context = new javax.naming.InitialContext();
            javax.sql.DataSource ds = (javax.sql.DataSource) context.lookup("java:comp/env/jdbc/libis_db");
            String sql = "UPDATE books SET isbn=?, title=?, author=?, published_year=?, genre=?, synopsis=? WHERE book_id=?";
            try (java.sql.Connection conn = ds.getConnection();
                 java.sql.PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, b.getIsbn());
                pst.setString(2, b.getTitle());
                pst.setString(3, b.getAuthor());
                pst.setInt(4, b.getPublishedYear());
                pst.setString(5, b.getGenre());
                pst.setString(6, b.getSynopsis());
                pst.setInt(7, b.getBookId());
                return pst.executeUpdate() > 0;
            }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

}