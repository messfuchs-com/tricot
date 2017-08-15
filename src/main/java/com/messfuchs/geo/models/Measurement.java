package com.messfuchs.geo.models;

import java.util.UUID;

public abstract class Measurement {

    private String uuid;
    public String station, target;

    public String getCompareString() {
        String s = "";
        s = s + getUuid();
        return s;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void generateUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}