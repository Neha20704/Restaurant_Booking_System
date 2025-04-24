package com.restaurant.booking.model;

public class MediumTable implements Table {
    private int id;

    public MediumTable(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getSize() {
        return 4;
    }
}
