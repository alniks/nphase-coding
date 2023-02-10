package com.nphase;

import com.nphase.entity.Category;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import com.nphase.service.ShoppingCartService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    static void test1() {
        Category categoryDrink = new Category("drinks");
        Category categoryFood = new Category("food");

        Product product1 = new Product(categoryDrink, "tea", BigDecimal.valueOf(5), 1);
        Product product2 = new Product(categoryDrink, "coffee", BigDecimal.valueOf(3.5), 2);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        ShoppingCart shoppingCart = new ShoppingCart(productList);
        BigDecimal bigDecimal = new ShoppingCartService().calculateTotalPrice(shoppingCart);

        System.out.println(bigDecimal);
    }

    static void test2() {
        Category categoryDrink = new Category("drinks");

        Product product1 = new Product(categoryDrink, "tea", BigDecimal.valueOf(5), 5);
        Product product2 = new Product(categoryDrink, "coffee", BigDecimal.valueOf(3.5), 3);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        ShoppingCart shoppingCart = new ShoppingCart(productList);
        BigDecimal bigDecimal = new ShoppingCartService().calculateTotalPrice(shoppingCart);

        System.out.println(bigDecimal);
    }

    static void test3() {
        Category categoryDrink = new Category("drinks");
        Category categoryFood = new Category("food");

        Product product1 = new Product(categoryDrink, "tea", BigDecimal.valueOf(5.3), 2);
        Product product2 = new Product(categoryDrink, "coffee", BigDecimal.valueOf(3.5), 2);
        Product product3 = new Product(categoryFood, "cheese", BigDecimal.valueOf(8), 2);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        ShoppingCart shoppingCart = new ShoppingCart(productList);
        BigDecimal bigDecimal = new ShoppingCartService().calculateTotalPrice(shoppingCart);

        System.out.println(bigDecimal);
    }
}
