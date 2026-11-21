package com.example.libis.service;

import com.example.libis.data.MemberDAO;
import com.example.libis.model.Member;
import java.util.List;

public class MemberService {
    private MemberDAO memberDAO = new MemberDAO();

    public List<Member> getAllMembers() {
        return memberDAO.findAll();
    }

    public boolean deleteMember(int memberId) {
        // In the future, you can add rules here (like "cannot delete yourself")
        return memberDAO.delete(memberId);
    }

    public boolean createMember(Member m) {
        return memberDAO.create(m);
    }

    public Member getMemberById(int id) {
        return memberDAO.findById(id);
    }

    public boolean updateMember(Member m) {
        return memberDAO.update(m);
    }
}