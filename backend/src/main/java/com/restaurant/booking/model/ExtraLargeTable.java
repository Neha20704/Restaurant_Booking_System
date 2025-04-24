package com.restaurant.booking.model;

public class ExtraLargeTable implements Table {
    private int id;

    public ExtraLargeTable(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getSize() {
        return 8;
    }
}
