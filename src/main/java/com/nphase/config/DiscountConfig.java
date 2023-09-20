package com.nphase.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DiscountConfig {
    private final int amount;
    private final double value;

    public DiscountConfig() {
        Properties properties = getProperties();
        amount = Integer.parseInt(properties.getProperty("discount.amount"));
        value = Double.parseDouble(properties.getProperty("discount.value"));
    }

    public int getAmount() {
        return amount;
    }

    public double getValue() {
        return value;
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        try {
            FileInputStream stream = new FileInputStream("src/main/resources/application.properties");
            props.load(stream);
        } catch (FileNotFoundException e) {
            System.err.println("Config file not found");
        } catch (IOException e) {
            System.err.println("Wrong config format");
        }
        return props;
    }
}
