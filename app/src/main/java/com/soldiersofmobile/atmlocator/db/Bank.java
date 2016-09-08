package com.soldiersofmobile.atmlocator.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "bank")
public class Bank {

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(canBeNull = false, unique = true)
    private String name;
    @DatabaseField(canBeNull = false)
    private String phone;

    public Bank() {
    }


    public Bank(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return name;
    }
}
