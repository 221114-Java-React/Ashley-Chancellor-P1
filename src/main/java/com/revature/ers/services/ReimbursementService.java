package com.revature.ers.services;

import com.revature.ers.daos.ReimbursementDAO;
import com.revature.ers.dtos.requests.NewReimbRequest;
import com.revature.ers.models.Reimbursement;

import java.util.Date;
import java.util.List;
import java.util.UUID;

// purpose: validate & retrieve reimb data from DAO
public class ReimbursementService {
    private final ReimbursementDAO reimbursementDAO;

    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }

    public Reimbursement submit(NewReimbRequest req, String authorId) {
        Reimbursement createdReimb = new Reimbursement(UUID.randomUUID().toString(), req.getAmount(), new Date(),
                null, req.getDescription(), req.getPaymentId(), authorId, null,
                "4eac4123-f552-4ea5-ab86-3ca7715e6f20" /* PENDING */, req.getTypeId());

        reimbursementDAO.save(createdReimb);
        return createdReimb;
    }

    public List<Reimbursement> getAllReimbs() {
        return reimbursementDAO.findAll();
    }

    public List<Reimbursement> getAllReimbsByAuthorId(String authorId) {
        return reimbursementDAO.findAllByAuthorId(authorId);
    }

    // helper functions
    public boolean isValidAmount(double amount) {
        return amount > 0 && amount < 5000;
    }

    public boolean isEmptyDescription(String description) {
        return description.isEmpty();
    }
}
