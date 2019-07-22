package com.example.queueingapplication;

public class Model_BeforeBay {

    private String order_type;
    private String product;
    private String qty;
    private String customer;

    public Model_BeforeBay(String order_type, String product, String qty, String customer) {
        this.order_type = order_type;
        this.product = product;
        this.qty = qty;
        this.customer = customer;
    }

    public String getOrder_type() {
        return order_type;
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
}
