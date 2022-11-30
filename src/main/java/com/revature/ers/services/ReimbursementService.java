package com.revature.ers.services;

import com.revature.ers.daos.ReimbursementDAO;
import com.revature.ers.dtos.requests.NewReimbRequest;
import com.revature.ers.models.Reimbursement;

import java.util.Date;
import java.util.UUID;

public class ReimbursementService {
    private final ReimbursementDAO reimbursementDAO;

    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }

    public Reimbursement submit(NewReimbRequest req) {
        Reimbursement createdReimb = new Reimbursement(UUID.randomUUID().toString(), req.getAmount(), new Date(),
                null, req.getDescription(), null, req.getPaymentId(), "", null,
                "", req.getTypeId());

        reimbursementDAO.save(createdReimb);
        return createdReimb;
    }
}
