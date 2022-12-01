package com.revature.ers.services;

import com.revature.ers.daos.ReimbursementDAO;
import com.revature.ers.dtos.requests.NewTicketRequest;
import com.revature.ers.models.Reimbursement;

import java.util.Date;
import java.util.UUID;

public class ReimbursementService {
    private final ReimbursementDAO reimbursementDAO;

    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }

    public Reimbursement submit(NewTicketRequest req) {
        Reimbursement createdTicket = new Reimbursement(UUID.randomUUID().toString(), req.getAmount(), new Date(),
                null, req.getDescription(), null, req.getPaymentId(), "", null,
                "4eac4123-f552-4ea5-ab86-3ca7715e6f20" /* PENDING */, req.getTypeId());

        reimbursementDAO.save(createdTicket);
        return createdTicket;
    }

    // helper functions
    public boolean isValidAmount(double amount) {
        return amount > 0 && amount < 5000;
    }

    public boolean isValidSubmitted(Date submitted) {
        return submitted.compareTo(new Date()) < 0;
    }

    public boolean isEmptyDescription(String description) {
        return description.isEmpty();
    }
}
