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

        return booksList;
    }

}