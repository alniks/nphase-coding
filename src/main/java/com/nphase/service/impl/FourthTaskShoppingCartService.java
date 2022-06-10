package com.nphase.service.impl;

import com.nphase.entity.Product;
import com.nphase.entity.ProductCategory;
import com.nphase.entity.ShoppingCart;
import com.nphase.service.ConfigurationReader;
import com.nphase.service.ShoppingCartService;
import com.nphase.service.config.PropertiesConfigurationReader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FourthTaskShoppingCartService implements ShoppingCartService {

    private static final int DIVIDING_SCALE_VALUE = 10;

    private final Map<ProductCategory, Integer> countProductsByCategory = new HashMap<>();

    private final Map<ProductCategory, BigDecimal> totalPriceByCategory = new HashMap<>();

    private final ConfigurationReader configurationReader = new PropertiesConfigurationReader();

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
                    && countProducts > configurationReader.getProductNumberForDiscount()) {
                final BigDecimal discountForCategory = configurationReader.getDiscountByCategory(category);
                final BigDecimal discountPercentage = discountForCategory.divide(
                        new BigDecimal("100"),
                        DIVIDING_SCALE_VALUE,
                        RoundingMode.HALF_UP);
                final BigDecimal discountCoefficient = BigDecimal.ONE.subtract(discountPercentage);
                totalPriceForCategory = totalPriceForCategory.multiply(discountCoefficient);
            }

            totalSum = totalSum.add(totalPriceForCategory);
        }

        return totalSum;
    }

}
