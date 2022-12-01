package com.revature.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.NewLoginRequest;
import com.revature.ers.dtos.requests.NewTicketRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.Reimbursement;
import com.revature.ers.models.User;
import com.revature.ers.services.ReimbursementService;
import com.revature.ers.services.TokenService;
import com.revature.ers.utils.custom_exceptions.InvalidTicketException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ReimbursementHandler {
    private final ReimbursementService reimbursementService;
    private final TokenService tokenService;
    private final ObjectMapper mapper;

    private final static Logger logger = LoggerFactory.getLogger(User.class);

    public ReimbursementHandler(ReimbursementService reimbursementService, TokenService tokenService, ObjectMapper mapper) {
        this.reimbursementService = reimbursementService;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    public void submitTicket(Context ctx) throws IOException {
        NewTicketRequest req = mapper.readValue(ctx.req.getInputStream(), NewTicketRequest.class);

        try {
            logger.info("Attempting to submit ticket...");

            String token = ctx.req.getHeader("authorization");

            if(token == null || token.isEmpty())
                throw new InvalidTicketException();

            Principal author = tokenService.extractRequesterDetails(token);

            if(author == null)
                throw new InvalidTicketException("Invalid token");

            Reimbursement createdTicket;

            if(reimbursementService.isValidAmount(req.getAmount())) {
                if(!reimbursementService.isEmptyDescription(req.getDescription()))
                    createdTicket = reimbursementService.submit(req, author.getUserId());
                else
                    throw new InvalidTicketException("Please enter a description");
            } else
                throw new InvalidTicketException("Amount must be between $0 and $5,000");

            ctx.status(201); // CREATED
            ctx.json(createdTicket);
            logger.info("Ticket submission attempt successful");
        } catch(InvalidTicketException e) {
            ctx.status(403); // FORBIDDEN
            ctx.json(e);
            e.printStackTrace();
            logger.info("Ticket submission attempt unsuccessful");
        }
    }
}
