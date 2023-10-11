package com.nphase.service;

import com.nphase.entity.Discount;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCartService {

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart, Discount discount) {
        BigDecimal result = new BigDecimal(0);
        Map<String, List<Product>> productsPerCategory = shoppingCart
                .getProducts()
                .stream()
                .collect(Collectors.groupingBy(Product::getCategory));

       for (Map.Entry<String, List<Product>> categoryProducts : productsPerCategory.entrySet()) {
           List<Product> categoryProductItems = categoryProducts.getValue();
           int quantityPerCategory = 0;
           for (Product product : categoryProductItems) {
               quantityPerCategory += product.getQuantity();
           }
           BigDecimal productsPrice = BigDecimal.valueOf(0);
           for (Product product : categoryProductItems) {
               BigDecimal productPrice =  product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
               if (quantityPerCategory > discount.getCount()) {
                   productPrice = productPrice.multiply(BigDecimal.valueOf(1 - discount.getPercentage()));
               }
               productsPrice = productsPrice.add(productPrice);
           }
           result = result.add(productsPrice);
       }


        return result;
    }
}
