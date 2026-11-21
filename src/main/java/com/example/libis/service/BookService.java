package com.example.libis.service;

import com.example.libis.data.BookDAO;
import com.example.libis.model.Book;
import java.util.List;

public class BookService {
    private BookDAO bookDAO = new BookDAO();

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public boolean deleteBook(int bookId) {
        return bookDAO.delete(bookId);
    }

    public boolean createBook(Book b) {
        return bookDAO.create(b);
    }

    public Book getBookById(int id) {
        return bookDAO.findById(id);
    }

    public boolean updateBook(Book b) {
        return bookDAO.update(b);
    }
}