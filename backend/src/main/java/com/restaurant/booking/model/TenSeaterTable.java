package com.restaurant.booking.model;

public class TenSeaterTable implements Table {
    private int id;

    public TenSeaterTable(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getSize() {
        return 10;
    }
}
