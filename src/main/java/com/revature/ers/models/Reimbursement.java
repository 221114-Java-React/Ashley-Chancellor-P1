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
    private String statusID;
    private String typeID;

    public Reimbursement() {
        super();
    }

    public Reimbursement(String id, double amount, Date submitted, Date resolved, String description, String receipt, String paymentID, String authorID, String resolverID, String statusID, String typeID) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.paymentID = paymentID;
        this.authorID = authorID;
        this.resolverID = resolverID;
        this.statusID = statusID;
        this.typeID = typeID;
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

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
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
                ", statusID='" + statusID + '\'' +
                ", typeID='" + typeID + '\'' +
                '}';
    }
}
