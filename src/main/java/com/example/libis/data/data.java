package com.example.libis.data;

import com.example.libis.model.Book;
import com.example.libis.model.Member;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class data implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        // Members - 5 students
        List<Member> members = new ArrayList<>();
        members.add(new Member(1, "Kamogelo", "Morerwa", "kamogelo@gmail.com", "pass123"));
        members.add(new Member(2, "Pako", "Daniel", "pakodaniel@gmail.com", "pako456"));
        members.add(new Member(3, "Kago", "Motladiile", "kagomotladiile@gmail.com", "kago789"));
        members.add(new Member(4, "Jennie", "Tebatso", "jennietebatso@gmail.com", "jennie321"));
        members.add(new Member(5, "Eunice", "Morerwa", "eunice@libis.ac.bw", "eunice654"));

        // Books - School subjects
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Understanding Mathematics", "T. M. Mmusi", "Mathematics",
                "This book covers basic algebra, geometry, and statistics for senior secondary students.", true));
        books.add(new Book(2, "Physical Science for Beginners", "K. P. Sebina", "Science",
                "An introduction to physics and chemistry concepts with practical experiments.", false));
        books.add(new Book(3, "History of Botswana", "M. L. Mokgosi", "History",
                "A comprehensive history of Botswana from pre-colonial times to independence.", true));
        books.add(new Book(4, "English Grammar Made Easy", "S. N. Kgosi", "English",
                "A simple guide to English grammar, punctuation, and essay writing.", true));
        books.add(new Book(5, "Computer Studies 101", "T. D. Ntwaagae", "ICT",
                "Basic computer skills including Microsoft Office and internet usage.", false));
        books.add(new Book(6, "Geography of Southern Africa", "R. M. Matlho", "Geography",
                "A detailed look at the physical and human geography of Southern Africa.", true));
        books.add(new Book(7, "Biology Life Sciences", "M. P. Ketshabile", "Biology",
                "An introduction to cell biology, genetics, and ecosystems.", true));
        books.add(new Book(8, "Business Studies Principles", "L. N. Mokgethi", "Business",
                "This book covers basic business concepts, entrepreneurship, and management.", false));
        books.add(new Book(9, "Setswana Literature", "B. T. Mothibedi", "Setswana",
                "A collection of Setswana poems, stories, and essays.", true));
        books.add(new Book(10, "Agricultural Science", "K. M. Tshosa", "Agriculture",
                "Practical agriculture including crop production, animal husbandry, and farming techniques.", true));

        context.setAttribute("members", members);
        context.setAttribute("books", books);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // Nothing needed
    }
}