package com.revature.ers.services;

import com.revature.ers.daos.ReimbursementDAO;
import com.revature.ers.dtos.requests.NewReimbRequest;
import com.revature.ers.models.Reimbursement;
import com.revature.ers.utils.custom_exceptions.InvalidStatusException;

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

    public Reimbursement approve(String id, String resolverId) {
        Reimbursement approvedReimb = reimbursementDAO.findByID(id);

        if(approvedReimb.getStatusId() != "4eac4123-f552-4ea5-ab86-3ca7715e6f20") // PENDING
            throw new InvalidStatusException("Ticket has already been approved or denied");

        approvedReimb.setResolved(new Date());
        approvedReimb.setResolverId(resolverId);
        approvedReimb.setStatusId("e601bb35-d2b6-4279-985f-3302889ed721"); // APPROVED
        reimbursementDAO.update(approvedReimb);
        return approvedReimb;
    }

    public Reimbursement deny(String id, String resolverId) {
        Reimbursement deniedReimb = reimbursementDAO.findByID(id);

        if(deniedReimb.getStatusId() != "4eac4123-f552-4ea5-ab86-3ca7715e6f20") // PENDING
            throw new InvalidStatusException("Ticket has already been approved or denied"); // APPROVED

        deniedReimb.setResolved(new Date());
        deniedReimb.setResolverId(resolverId);
        deniedReimb.setStatusId("02f8e3b9-88a1-4259-b133-d8b5ba2861fb");
        reimbursementDAO.update(deniedReimb);
        return deniedReimb;
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
