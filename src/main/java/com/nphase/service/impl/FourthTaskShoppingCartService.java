package com.nphase.service.impl;

import com.nphase.entity.Product;
import com.nphase.entity.ProductCategory;
import com.nphase.entity.ShoppingCart;
import com.nphase.service.ConfigurationReader;
import com.nphase.service.ShoppingCartService;
import com.nphase.service.config.PropertiesConfigurationReader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FourthTaskShoppingCartService implements ShoppingCartService {

    private final int DIVIDING_SCALE_VALUE = 10;

    private final Map<ProductCategory, Integer> numberProductsByCategory = new HashMap<>();

    private final List<ProductCategory> productFordiscount = new ArrayList<>();

    private ConfigurationReader configurationReader = new PropertiesConfigurationReader();

    @Override
    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {

        BigDecimal totalSum = BigDecimal.ZERO;
        List<Product> products = shoppingCart.getProducts();
        for (Product product : products) {
            final int quantity = product.getQuantity();
            final ProductCategory category = product.getCategory();

            Integer numberProducts = numberProductsByCategory.get(category);
            if (numberProducts != null && numberProducts > configurationReader.getProductNumberForDiscount()) {
                productFordiscount.add(category);
            } else {
                numberProducts = numberProducts + quantity;
                numberProductsByCategory.put(category, numberProducts);
            }
        }

        for (Product product : products) {

            final BigDecimal productSum = calculateProductSum(
                    product.getPricePerUnit(),
                    product.getQuantity(),
                    product.getCategory());

            totalSum = totalSum.add(productSum);
        }

        return totalSum;
    }


    private BigDecimal calculateProductSum(BigDecimal price, int quantity, ProductCategory category) {
        boolean isDiscount = productFordiscount.contains(category);

        BigDecimal resultPrice = price.multiply(new BigDecimal(quantity));
        if (isDiscount) {
            final BigDecimal discountByCategory = configurationReader.getDiscountByCategory(category);
            final BigDecimal discountPercentage = discountByCategory.divide(
                    new BigDecimal("100"),
                    DIVIDING_SCALE_VALUE,
                    RoundingMode.HALF_UP);
            final BigDecimal discountCoefficient = BigDecimal.ONE.subtract(discountPercentage);
            resultPrice = resultPrice.multiply(discountCoefficient);
        }

        return resultPrice;
    }

}
