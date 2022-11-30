package com.revature.ers.dtos.requests;

import java.util.Date;

public class NewReimbRequest {
    private double amount;
    private String description;
    private String paymentId;
    private String typeId;

    public NewReimbRequest() {
        super();
    }

    public NewReimbRequest(double amount, String description, String paymentId, String typeId) {
        this.amount = amount;
        this.description = description;
        this.paymentId = paymentId;
        this.typeId = typeId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "NewReimbRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", typeId='" + typeId + '\'' +
                '}';
    }
}
