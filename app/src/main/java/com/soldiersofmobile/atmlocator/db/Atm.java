package com.soldiersofmobile.atmlocator.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "atm")
public class Atm {

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(canBeNull = false)
    private String address;
    @DatabaseField(uniqueCombo = true, canBeNull = false)
    private double lat;
    @DatabaseField(uniqueCombo = true, canBeNull = false)
    private double lng;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Bank bank;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
