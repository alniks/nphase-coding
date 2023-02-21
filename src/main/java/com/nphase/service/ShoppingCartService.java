package com.nphase.service;

import com.nphase.discount.Discount;
import com.nphase.entity.Category;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ShoppingCartService {
    private final Discount discount;

    public ShoppingCartService(final Discount discount) {
        this.discount = Objects.requireNonNull(discount, "discount");
    }

    private static Map<Category, List<Product>> groupByCategory(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts().stream().collect(
                Collectors.groupingBy(Product::getCategory));
    }

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return groupByCategory(shoppingCart)
                .entrySet().stream()
                .map(e -> discountedPrice(e.getKey(), e.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal discountedPrice(Category category, List<Product> products) {
        return products.size() > discount.minProductsCountForDiscount()
                ? totalPrice(products).multiply(discount.resolveDiscountMultiplier(category))
                : totalPrice(products);
    }

    private BigDecimal totalPrice(List<Product> products) {
        return products.stream()
                .map(it -> it.getPricePerUnit().multiply(BigDecimal.valueOf(it.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
