package com.nphase.service;

import com.nphase.entity.ProductCategory;

import java.math.BigDecimal;

public interface ConfigurationReader {

    Integer getProductNumberForDiscount();

    BigDecimal getDiscountByCategory(ProductCategory category);

}
