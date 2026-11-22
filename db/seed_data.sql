-- THE SEED DATA WITH 5 MEMERS AND 1 LIBRARIAN
INSERT INTO members (member_number, full_name, email, username, password, role) VALUES
                                                                                    ('MEM001', 'Kamogelo Morerwa', 'kamogelo@gmail.com', 'kamogelo', 'pass123', 'member'),
                                                                                    ('MEM002', 'Pako Daniel', 'pako@gmail.com', 'pako', 'pass123', 'member'),
                                                                                    ('MEM003', 'Kago Motladiile', 'kago@gmail.com', 'kago', 'pass123', 'member'),
                                                                                    ('MEM004', 'Jennie Tebatso', 'jennie@gmail.com', 'jennie', 'pass123', 'member'),
                                                                                    ('MEM005', 'Eunice Morerwa', 'eunice@gmail.com', 'eunice', 'pass123', 'member'),
                                                                                    ('LIB001', 'Librarian Admin', 'admin@libis.com', 'admin', 'admin123', 'librarian');

-- THE SEED DATA WITH 10 BOOKS
INSERT INTO books (isbn, title, author, published_year, genre, synopsis) VALUES
                                                                             ('978-3-16-148410-0', 'Understanding Mathematics', 'T. M. Mmusi', 2015, 'Mathematics', 'A basic math book.'),
                                                                             ('978-1-4028-9462-6', 'Physical Science', 'K. P. Sebina', 2018, 'Science', 'Intro to physics.'),
                                                                             ('978-0-306-40615-7', 'History of Botswana', 'M. L. Mokgosi', 2010, 'History', 'Botswana history.'),
                                                                             ('978-0-8044-2957-4', 'English Grammar', 'S. N. Kgosi', 2020, 'English', 'Grammar guide.'),
                                                                             ('978-0-375-50167-4', 'Computer Studies', 'T. D. Ntwaagae', 2021, 'ICT', 'Basic computers.'),
                                                                             ('978-0-394-80001-1', 'Geography', 'R. M. Matlho', 2012, 'Geography', 'Southern Africa.'),
                                                                             ('978-0-679-72021-0', 'Biology', 'M. P. Ketshabile', 2019, 'Biology', 'Life sciences.'),
                                                                             ('978-0-14-017739-8', 'Business Studies', 'L. N. Mokgethi', 2017, 'Business', 'Intro to business.'),
                                                                             ('978-0-06-093546-9', 'Setswana Literature', 'B. T. Mothibedi', 2014, 'Setswana', 'Setswana poems.'),
                                                                             ('978-0-7432-7356-5', 'Agricultural Science', 'K. M. Tshosa', 2016, 'Agriculture', 'Farming basics.');

-- 6. Seed Data: Loans
-- Kamogelo (ID 1) has 3 open loans (to test the limit!)
INSERT INTO loans (member_id, book_id, borrowed_on, returned_on) VALUES
                                                                     (1, 1, '2026-11-01', NULL),
                                                                     (1, 2, '2026-11-02', NULL),
                                                                     (1, 3, '2026-11-03', NULL);

-- Pako (ID 2) has 1 returned loan and 1 open loan
INSERT INTO loans (member_id, book_id, borrowed_on, returned_on) VALUES
                                                                     (2, 4, '2026-10-01', '2026-10-15'),
                                                                     (2, 5, '2026-11-10', NULL);

-- Jennie (ID 4) has 1 returned loan
INSERT INTO loans (member_id, book_id, borrowed_on, returned_on) VALUES
    (4, 6, '2026-09-01', '2026-09-20');