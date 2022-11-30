package com.revature.ers.models;

public class ReimbType {
    private String id;
    private Type type;

    public ReimbType() {
        super();
    }

    public ReimbType(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ReimbType{" +
                "id='" + id + '\'' +
                ", type=" + type +
                '}';
    }
}
enum Type {
    LODGING, TRAVEL, FOOD, OTHER
}
