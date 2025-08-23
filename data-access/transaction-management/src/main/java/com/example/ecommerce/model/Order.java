package com.example.ecommerce.model;

public class Order {
    private long id;
    private String customerName;
    private String status;

    public Order(long id, String customerName, String status) {
        this.id = id;
        this.customerName = customerName;
        this.status = status;
    }

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Order{"
                + "id=" + id +
                ", customerName='" + customerName + "'" +
                ", status='" + status + "'" +
                '}';
    }
}
