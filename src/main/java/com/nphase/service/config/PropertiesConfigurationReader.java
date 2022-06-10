package com.nphase.service.config;

import com.nphase.entity.ProductCategory;
import com.nphase.service.ConfigurationReader;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Properties;

public class PropertiesConfigurationReader implements ConfigurationReader {

    private static final String CONFIG_PATH = "configuration.properties";
    private static final String DISCOUNT_PROPERTIES_KEY_PATTERN = "discount.%s";

    private Properties props;

    @Override
    public Integer getProductNumberForDiscount() {
        return Integer.valueOf(props.getProperty("product.number.per.category.for.discount"));
    }

    @Override
    public BigDecimal getDiscountByCategory(ProductCategory category) {
        final String categoryDiscountName = String.format(DISCOUNT_PROPERTIES_KEY_PATTERN, category.name().toLowerCase(Locale.ROOT));
        return new BigDecimal(props.getProperty(categoryDiscountName));
    }

    public PropertiesConfigurationReader() {
        this.props = new Properties();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream resourceStream = loader.getResourceAsStream(CONFIG_PATH)) {
            props.load(resourceStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
