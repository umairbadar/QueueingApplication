package com.example.queueingapplication;

public class Model_ChamberDetails {

    private String chamber_name;
    private String fillable_volume;
    private String setVolume;

    public Model_ChamberDetails(String chamber_name, String fillable_volume, String setVolume) {
        this.chamber_name = chamber_name;
        this.fillable_volume = fillable_volume;
        this.setVolume = setVolume;
    }

    public String getChamber_name() {
        return chamber_name;
    }

    public String getFillable_volume() {
        return fillable_volume;
    }

    public String getSetVolume() {
        return setVolume;
    }
}
