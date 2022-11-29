package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCartService {

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .map(p -> BigDecimal.valueOf(p.getQuantity()).multiply(p.getPricePerUnit()))
                .reduce(BigDecimal.valueOf(0), BigDecimal::add)
                .setScale(1, RoundingMode.CEILING);
    }

    public BigDecimal calculateTotalPriceWithDiscount(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .map(this::calculateProductPriceWithDiscount)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add)
                .setScale(1, RoundingMode.CEILING);
    }

    private BigDecimal calculateProductPriceWithDiscount(Product product) {
        BigDecimal originalPrice = BigDecimal.valueOf(product.getQuantity()).multiply(product.getPricePerUnit());
        if (product.getQuantity() > 3) {
            return originalPrice.multiply(BigDecimal.valueOf(0.9));
        } else return originalPrice;
    }

    public BigDecimal calculateTotalPriceWithDiscountPerCategory(ShoppingCart shoppingCart) {
        Map<String, List<Product>> productsPerCategory = shoppingCart.getProducts()
                .stream()
                .collect(Collectors.groupingBy(Product::getCategory));
        return productsPerCategory.values()
                .stream()
                .map(this::calculateCategoryPriceWithDiscount)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add)
                .setScale(2, RoundingMode.CEILING);
    }

    private BigDecimal calculateCategoryPriceWithDiscount(List<Product> itemsInCategory) {
        int numberOfItems = itemsInCategory
                .stream()
                .mapToInt(Product::getQuantity)
                .sum();
        BigDecimal originalPrice = itemsInCategory
                .stream().map(p -> BigDecimal.valueOf(p.getQuantity()).multiply(p.getPricePerUnit()))
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
        if (numberOfItems > 3) {
            return originalPrice.multiply(BigDecimal.valueOf(0.9));
        } else return originalPrice;
    }
}
