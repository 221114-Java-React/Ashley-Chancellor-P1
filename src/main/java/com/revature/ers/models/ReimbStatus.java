package com.revature.ers.models;

public class ReimbStatus {
    private String id;
    private Status status;

    public ReimbStatus() {
        super();
    }

    public ReimbStatus(String id, Status status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReimbStatus{" +
                "id='" + id + '\'' +
                ", status=" + status +
                '}';
    }
}
enum Status {
    PENDING, APPROVED, DENIED
}
