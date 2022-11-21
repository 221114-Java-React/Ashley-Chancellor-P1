public class Ticket {
    private double amount;
    private String description;
    //private String status;

    public Ticket() {
        this.amount = 0;
        this.description = null;
    }

    public Ticket(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if(amount <= 0)
            throw new InvalidTicketException("Please specify an amount for reimbursement.");
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description == null)
            throw new InvalidTicketException("Please add a description to the ticket.");
        this.description = description;
    }

    /*public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/
}

enum Status {
    PENDING,
    APPROVED,
    REJECTED
}
