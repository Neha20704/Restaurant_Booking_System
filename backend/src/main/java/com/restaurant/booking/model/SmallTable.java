package com.restaurant.booking.model;

public class SmallTable implements Table {
    private int id;

    public SmallTable(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getSize() {
        return 2;
    }
}
