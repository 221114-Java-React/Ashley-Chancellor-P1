package com.revature.ers.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.NewReimbRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.Reimbursement;
import com.revature.ers.models.User;
import com.revature.ers.services.ReimbursementService;
import com.revature.ers.services.TokenService;
import com.revature.ers.utils.custom_exceptions.InvalidAuthException;
import com.revature.ers.utils.custom_exceptions.InvalidStatusException;
import com.revature.ers.utils.custom_exceptions.InvalidTicketException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

// purpose: handle http verbs & endpoints
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
        NewReimbRequest req = mapper.readValue(ctx.req.getInputStream(), NewReimbRequest.class);

        try {
            String token = ctx.req.getHeader("authorization");

            if(token == null || token.isEmpty())
                throw new InvalidAuthException();

            Principal author = tokenService.extractRequesterDetails(token);

            if(author == null)
                throw new InvalidAuthException("Invalid token");

            logger.info("Attempting to submit ticket...");
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
        } catch(InvalidAuthException e) {
            ctx.status(401); // UNAUTHORIZED
            ctx.json(e);
            logger.info("Please log in to submit a ticket");
        } catch(InvalidTicketException e) {
            ctx.status(403); // FORBIDDEN
            ctx.json(e);
            logger.info("Ticket submission attempt unsuccessful");
        }
    }

    public void approveTicket(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");

            if(token == null || token.isEmpty())
                throw new InvalidAuthException("You are not signed in");

            Principal principal = tokenService.extractRequesterDetails(token);

            if(principal == null)
                throw new InvalidAuthException("Invalid token");

            if(!principal.getRoleId().equals("8aa97508-5e36-472d-b831-94e252790863")) // FINANCE_MANAGER
                throw new InvalidAuthException("You are not authorized to do this");

            String id = ctx.req.getParameter("id");
            Reimbursement approvedTicket = reimbursementService.approve(id, principal.getUserId());
            ctx.json(approvedTicket);
            logger.info("Ticket approved");
        } catch(InvalidAuthException e) {
            ctx.status(401); // UNAUTHORIZED
            ctx.json(e);
        } catch(InvalidStatusException e) {
            ctx.status(400);
            ctx.json(e);
        }
    }

    public void denyTicket(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");

            if(token == null || token.isEmpty())
                throw new InvalidAuthException("You are not signed in");

            Principal principal = tokenService.extractRequesterDetails(token);

            if(principal == null)
                throw new InvalidAuthException("Invalid token");

            if(!principal.getRoleId().equals("8aa97508-5e36-472d-b831-94e252790863")) // FINANCE_MANAGER
                throw new InvalidAuthException("You are not authorized to do this");

            String id = ctx.req.getParameter("id");
            Reimbursement deniedTicket = reimbursementService.deny(id, principal.getUserId());
            ctx.json(deniedTicket);
            logger.info("Ticket denied");
        } catch(InvalidAuthException e) {
            ctx.status(401); // UNAUTHORIZED
            ctx.json(e);
        } catch(InvalidStatusException e) {
            ctx.status(400);
            ctx.json(e);
        }
    }

    public void getAllTickets(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");

            if(token == null || token.isEmpty())
                throw new InvalidAuthException("You are not signed in");

            Principal principal = tokenService.extractRequesterDetails(token);

            if(principal == null)
                throw new InvalidAuthException("Invalid token");

            if(!principal.getRoleId().equals("8aa97508-5e36-472d-b831-94e252790863")) // FINANCE_MANAGER
                throw new InvalidAuthException("You are not authorized to do this");

            List<Reimbursement> tickets = reimbursementService.getAllReimbs();
            ctx.json(tickets);
        } catch(InvalidAuthException e) {
            ctx.status(401); // UNAUTHORIZED
            ctx.json(e);
        }
    }

    public void getAllTicketsByAuthorId(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");

            if(token == null || token.isEmpty())
                throw new InvalidAuthException("You are not signed in");

            Principal principal = tokenService.extractRequesterDetails(token);

            if(principal == null)
                throw new InvalidAuthException("Invalid token");

            String authorId = ctx.req.getParameter("authorId");

            if(!(principal.getUserId().equals(authorId) // user that submitted ticket
                    || principal.getRoleId().equals("8aa97508-5e36-472d-b831-94e252790863"))) // FINANCE MANAGER
                throw new InvalidAuthException("You are not authorized to do this");

            List<Reimbursement> tickets = reimbursementService.getAllReimbsByAuthorId(authorId);
            ctx.json(tickets);
        } catch(InvalidAuthException e) {
            ctx.status(401); // UNAUTHORIZED
            ctx.json(e);
        }
    }

    public void getAllTicketsByResolverId(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");

            if(token == null || token.isEmpty())
                throw new InvalidAuthException("You are not signed in");

            Principal principal = tokenService.extractRequesterDetails(token);

            if(principal == null)
                throw new InvalidAuthException("Invalid token");

            String resolverId = ctx.req.getParameter("resolverId");

            if(!principal.getRoleId().equals("8aa97508-5e36-472d-b831-94e252790863")) // FINANCE_MANAGER
                throw new InvalidAuthException("You are not authorized to do this");

            List<Reimbursement> tickets = reimbursementService.getAllReimbsByResolverId(resolverId);
            ctx.json(tickets);
        } catch(InvalidAuthException e) {
            ctx.status(401); // UNAUTHORIZED
            ctx.json(e);
        }
    }

    public void getAllTicketsByStatusId(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");

            if(token == null || token.isEmpty())
                throw new InvalidAuthException("You are not signed in");

            Principal principal = tokenService.extractRequesterDetails(token);

            if(principal == null)
                throw new InvalidAuthException("Invalid token");

            String statusId = ctx.req.getParameter("statusId");

            if(!principal.getRoleId().equals("8aa97508-5e36-472d-b831-94e252790863")) // FINANCE_MANAGER
                throw new InvalidAuthException("You are not authorized to do this");

            List<Reimbursement> tickets = reimbursementService.getAllReimbsByStatusId(statusId);
            ctx.json(tickets);
        } catch(InvalidAuthException e) {
            ctx.status(401); // UNAUTHORIZED
            ctx.json(e);
        }
    }

    public void getAllTicketsByTypeId(Context ctx) {
        try {
            String token = ctx.req.getHeader("authorization");

            if(token == null || token.isEmpty())
                throw new InvalidAuthException("You are not signed in");

            Principal principal = tokenService.extractRequesterDetails(token);

            if(principal == null)
                throw new InvalidAuthException("Invalid token");

            String typeId = ctx.req.getParameter("typeId");

            if(!principal.getRoleId().equals("8aa97508-5e36-472d-b831-94e252790863")) // FINANCE_MANAGER
                throw new InvalidAuthException("You are not authorized to do this");

            List<Reimbursement> tickets = reimbursementService.getAllReimbsByTypeId(typeId);
            ctx.json(tickets);
        } catch(InvalidAuthException e) {
            ctx.status(401); // UNAUTHORIZED
            ctx.json(e);
        }
    }
}
