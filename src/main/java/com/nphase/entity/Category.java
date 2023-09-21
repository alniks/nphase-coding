package com.nphase.entity;

public enum Category {
    FOOD(3, 0.9),
    DRINKS(3, 0.9);

    private final int amount;

    private final double discount;

    public int getAmount() {
        return amount;
    }

    public double getDiscount() {
        return discount;
    }

    Category(int amount, double discount) {
        this.amount = amount;
        this.discount = discount;
    }
}
