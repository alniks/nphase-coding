package com.nphase.service;

import com.nphase.entity.Category;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.HashMap;

public class ShoppingCartService {

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(Product product : shoppingCart.getProducts()) {
            totalPrice = totalPrice.add(product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity())));
        }
        return totalPrice;
    }

    public BigDecimal calculateDiscountTotalPrice(ShoppingCart shoppingCart) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(Product product : shoppingCart.getProducts()) {
            totalPrice = totalPrice.add(product.getPricePerUnit()
                    .multiply(BigDecimal.valueOf(product.getQuantity()))
                    .multiply(BigDecimal.valueOf(product.getQuantity() > product.getCategory().getAmount() ? product.getCategory().getDiscount() : 1)));
        }
        return totalPrice;
    }

    public BigDecimal calculateCategoryDiscountTotalPrice(ShoppingCart shoppingCart) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        HashMap<Category, Integer> categoryCount = new HashMap<>();
        for(Product product : shoppingCart.getProducts()) {
            if (categoryCount.get(product.getCategory()) == null) {
                categoryCount.put(product.getCategory(), product.getQuantity());
            } else {
                categoryCount.put(product.getCategory(), categoryCount.get(product.getCategory()) + product.getQuantity());
            }

        }
        for(Product product : shoppingCart.getProducts()) {
            totalPrice = totalPrice.add(product.getPricePerUnit()
                    .multiply(BigDecimal.valueOf(product.getQuantity()))
                    .multiply(BigDecimal.valueOf(categoryCount.get(product.getCategory()) > product.getCategory().getAmount() ? product.getCategory().getDiscount() : 1)));
        }
        return totalPrice;
    }
}
