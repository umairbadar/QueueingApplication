package com.example.queueingapplication;

public class Model_AtBay {

    private String oder_type;
    private String product;
    private String qty;
    private String customer;
    private String bay;

    public Model_AtBay(String oder_type, String product, String qty, String customer, String bay) {
        this.oder_type = oder_type;
        this.product = product;
        this.qty = qty;
        this.customer = customer;
        this.bay = bay;
    }

    public String getOder_type() {
        return oder_type;
    }

    public String getProduct() {
        return product;
    }

    public String getQty() {
        return qty;
    }

    public String getCustomer() {
        return customer;
    }

    public String getBay() {
        return bay;
    }
}
