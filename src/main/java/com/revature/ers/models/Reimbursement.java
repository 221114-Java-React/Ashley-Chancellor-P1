package com.revature.ers.models;

import java.util.Date;

public class Reimbursement {
    private String id;
    private double amount;
    private Date submitted;
    private Date resolved;
    private String description;
    private String receipt;
    private String paymentID;
    private String authorID;
    private String resolverID;
    private ReimbStatus status;
    private ReimbType type;

    public Reimbursement() {
        super();
    }

    public Reimbursement(String id, double amount, Date submitted, Date resolved, String description, String receipt,
                         String paymentID, String authorID, String resolverID, ReimbStatus status, ReimbType type) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.paymentID = paymentID;
        this.authorID = authorID;
        this.resolverID = resolverID;
        this.status = status;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Date submitted) {
        this.submitted = submitted;
    }

    public Date getResolved() {
        return resolved;
    }

    public void setResolved(Date resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getResolverID() {
        return resolverID;
    }

    public void setResolverID(String resolverID) {
        this.resolverID = resolverID;
    }

    public ReimbStatus getStatus() {
        return status;
    }

    public void setStatus(ReimbStatus status) {
        this.status = status;
    }

    public ReimbType getType() {
        return type;
    }

    public void setType(ReimbType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", receipt='" + receipt + '\'' +
                ", paymentID='" + paymentID + '\'' +
                ", authorID='" + authorID + '\'' +
                ", resolverID='" + resolverID + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
