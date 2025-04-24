package com.restaurant.booking.model;

public class LargeTable implements Table {
    private int id;

    public LargeTable(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getSize() {
        return 6;
    }
}
