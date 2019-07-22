package com.example.queueingapplication;

public class Model_Control_Room {

    private String skully;
    private String overFill;
    private String progress;
    private String fillable_qty;
    private String chamber_name;
    private String order_id;
    private String type;
    private String product;
    private String quantity;
    private String sapref;
    private String tank;
    private String bay;
    private String arm;
    private String vehicle;
    private String lorry;
    private String driver;
    private String customer;
    private String batch;

    public Model_Control_Room(String skully,String overFill,String progress, String fillable_qty, String chamber_name, String order_id, String type, String product, String quantity, String sapref, String tank, String bay, String arm, String vehicle, String lorry
            , String driver, String customer, String batch)
    {
        this.type=type;
        this.product=product;
        this.quantity=quantity;
        this.sapref=sapref;
        this.tank=tank;
        this.bay=bay;
        this.arm=arm;
        this.vehicle=vehicle;
        this.lorry=lorry;
        this.driver=driver;
        this.customer=customer;
        this.batch=batch;
        this.order_id=order_id;
        this.chamber_name=chamber_name;
        this.fillable_qty=fillable_qty;
        this.skully=skully;
        this.overFill=overFill;
        this.progress=progress;
    }

    public String getSkully() {
        return skully;
    }

    public String getOverFill() {
        return overFill;
    }

    public String getProgress() {
        return progress;
    }

    public String getFillable_qty() {
        return fillable_qty;
    }

    public String getChamber_name() {
        return chamber_name;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getType()
    {
        return type;
    }
    public String getProduct()
    {
        return product;
    }
    public String getQuantity()
    {
        return quantity;
    }

    public String getSapref() {
        return sapref;
    }

    public String getTank() {
        return tank;
    }

    public String getBay() {
        return bay;
    }

    public String getArm() {
        return arm;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getLorry() {
        return lorry;
    }

    public String getDriver() {
        return driver;
    }
    public String getCustomer()
    {
        return customer;
    }
    public String getBatch()
    {
        return batch;
    }
}