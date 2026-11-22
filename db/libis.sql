DROP DATABASE IF EXISTS libis;
CREATE DATABASE libis;
USE libis;

-- this creates members tafole
CREATE TABLE members (
                         member_id INT AUTO_INCREMENT PRIMARY KEY,
                         member_number VARCHAR(20) UNIQUE NOT NULL,
                         full_name VARCHAR(100) NOT NULL,
                         email VARCHAR(100) UNIQUE NOT NULL,
                         username VARCHAR(50) UNIQUE NOT NULL,
                         password VARCHAR(50) NOT NULL,
                         role VARCHAR(20) NOT NULL DEFAULT 'member'
);

-- this creates the books tafole
CREATE TABLE books (
                       book_id INT AUTO_INCREMENT PRIMARY KEY,
                       isbn VARCHAR(20) UNIQUE NOT NULL,
                       title VARCHAR(100) NOT NULL,
                       author VARCHAR(100) NOT NULL,
                       published_year INT,
                       genre VARCHAR(50),
                       synopsis TEXT
);

-- CREATES THE LOANS TABLE
CREATE TABLE loans (
                       loan_id INT AUTO_INCREMENT PRIMARY KEY,
                       member_id INT NOT NULL,
                       book_id INT NOT NULL,
                       borrowed_on DATE NOT NULL,
                       returned_on DATE,
                       FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE,
                       FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE
);
