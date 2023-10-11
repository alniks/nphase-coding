package com.nphase.entity;

public class Discount {

    private final long count;
    private final double percentage;

    public Discount(long count, double percentage) {
        this.count = count;
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

    public long getCount() {
        return count;
    }
}
