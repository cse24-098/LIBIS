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
            String sql = "SELECT * FROM books";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            // 3. Loop through the results and create Book objects
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                book.setSynopsis(rs.getString("synopsis"));
                book.setAvailable(rs.getBoolean("available"));

                booksList.add(book);
            }

        } catch (Exception e) {
            System.out.println("Error fetching books: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close everything
            if (rs != null) { try { rs.close(); } catch (Exception e) {} }
            if (pst != null) { try { pst.close(); } catch (Exception e) {} }
            if (conn != null) { try { conn.close(); } catch (Exception e) {} }
        }

        return booksList;
    }
}