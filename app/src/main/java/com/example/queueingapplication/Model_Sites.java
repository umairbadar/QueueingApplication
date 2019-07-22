package com.example.queueingapplication;

public class Model_Sites {

    private String site_id;
    private String site_name;
    private String manager_name;

    public Model_Sites(String site_id, String site_name, String manager_name) {
        this.site_id = site_id;
        this.site_name = site_name;
        this.manager_name = manager_name;
    }

    public String getSite_id() {
        return site_id;
    }

    public String getSite_name() {
        return site_name;
    }

    public String getManager_name() {
        return manager_name;
    }
}
