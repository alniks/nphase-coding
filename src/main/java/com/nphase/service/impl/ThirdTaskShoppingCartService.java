package com.nphase.service.impl;

import com.nphase.entity.Product;
import com.nphase.entity.ProductCategory;
import com.nphase.entity.ShoppingCart;
import com.nphase.service.ShoppingCartService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdTaskShoppingCartService implements ShoppingCartService {

    private static final Integer NUMBER_PRODUCT_FOR_DISCOUNT_IN_CATEGORY = 3;

    private static final BigDecimal DISCOUNT_COEFF = new BigDecimal("0.9");

    private final Map<ProductCategory, Integer> countProductsByCategory = new HashMap<>();

    private final Map<ProductCategory, BigDecimal> totalPriceByCategory = new HashMap<>();

    @Override
    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        List<Product> products = shoppingCart.getProducts();

        products.forEach(this::processProductsItems);

        return calculateTotalPrice();
    }

    private void processProductsItems(Product product) {
        countProductsItems(product);
        countItemsTotalPrice(product);
    }

    private void countProductsItems(Product product) {
        final ProductCategory category = product.getCategory();
        Integer productsCount = countProductsByCategory.get(category);
        if (productsCount == null) {
            productsCount = 0;
        }

        final int quantity = product.getQuantity();
        final Integer updatedProductsCount = productsCount + quantity;

        countProductsByCategory.put(category, updatedProductsCount);
    }

    private void countItemsTotalPrice(Product product) {
        final ProductCategory category = product.getCategory();

        BigDecimal productsTotalPrice = totalPriceByCategory.get(category);
        if (productsTotalPrice == null) {
            productsTotalPrice = BigDecimal.ZERO;
        }

        final BigDecimal price = product.getPricePerUnit();
        final int quantity = product.getQuantity();
        final BigDecimal productsPrice = price.multiply(BigDecimal.valueOf(quantity));
        final BigDecimal updatedTotalPrice = productsTotalPrice.add(productsPrice);

        totalPriceByCategory.put(category, updatedTotalPrice);
    }

    private BigDecimal calculateTotalPrice() {
        BigDecimal totalSum = BigDecimal.ZERO;

        for (Map.Entry<ProductCategory, BigDecimal> totalPriceCategory : totalPriceByCategory.entrySet()) {
            final ProductCategory category = totalPriceCategory.getKey();
            BigDecimal totalPriceForCategory = totalPriceCategory.getValue();

            final Integer countProducts = countProductsByCategory.get(category);
            if (countProducts != null
                    && countProducts > NUMBER_PRODUCT_FOR_DISCOUNT_IN_CATEGORY) {
                totalPriceForCategory = totalPriceForCategory.multiply(DISCOUNT_COEFF);
            }

            totalSum = totalSum.add(totalPriceForCategory);
        }

        return totalSum;
    }

}
