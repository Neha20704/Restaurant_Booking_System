package com.restaurant.booking.model;

public class TableFactory {
    public Table createTable(int id, int size) {
        switch (size) {
            case 2: return new SmallTable(id);
            case 4: return new MediumTable(id);
            case 6: return new LargeTable(id);
            case 8: return new ExtraLargeTable(id);
            case 10: return new TenSeaterTable(id);
            default: throw new IllegalArgumentException("Unsupported table size: " + size);
        }
    }
}
