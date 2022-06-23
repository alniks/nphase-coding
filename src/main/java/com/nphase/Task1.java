package com.nphase;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.service.ShoppingCartService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Task1 {
    public static void main(String[] args) {
        List<Product> productList = new ArrayList<>();
        Product oneTea = new Product("tea", new BigDecimal(5), 1);
        Product twoCoffee = new Product("coffee", new BigDecimal(3.5), 2);
        productList.add(oneTea);
        productList.add(twoCoffee);
        ShoppingCart shoppingCart = new ShoppingCart(productList);
        ShoppingCartService cartService = new ShoppingCartService();
        cartService.calculateTotalPrice(shoppingCart);
        BigDecimal total = cartService.calculateTotalPrice(shoppingCart);
        System.out.println("Shopping cart total = " + total);
    }
}
