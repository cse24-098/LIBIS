package com.example.libis.service;

import com.example.libis.data.LoanDAO;

public class LoanService {

    private LoanDAO loanDAO = new LoanDAO();

    // Returns true if they can borrow, false if they hit the limit
    public boolean canBorrow(int memberId) {
        int openLoans = loanDAO.countOpenLoans(memberId);
        return openLoans < 3; // 3 is the maximum allowed
    }
}