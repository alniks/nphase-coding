package com.nphase;

import com.nphase.entity.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductBuilder {
    public Product build(String name, BigDecimal pricePerUnit, int quantity){
        if(quantity>=3){
            return new Product(name,pricePerUnit.multiply(new BigDecimal(0.9)).setScale(2, RoundingMode.HALF_UP),quantity);
        }
        return new Product(name,pricePerUnit,quantity);
    }
}
