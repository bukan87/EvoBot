package ru.evotor.bot.db.dao;

import org.springframework.stereotype.Repository;
import ru.evotor.bot.config.Property;
import ru.evotor.bot.db.entity.ProductInNearestShop;
import ru.evotor.bot.db.mapper.ProductMapper;

import java.util.List;

/**
 * @author a.ilyin
 */
@Repository
public class ProductDAO {

    private final ProductMapper productMapper;
    private final Property property;

    public ProductDAO(ProductMapper productMapper,
                      Property property ){
        this.productMapper = productMapper;
        this.property = property;
    }

    public List<ProductInNearestShop> getProductsInNearestShops(String productName, float latitude, float longitude){
        return productMapper.getProductsInNearestShops(productName, getLocation(latitude, longitude), property.getMaxDistance(), property.getMaxShops());
    }

    private String getLocation(float latitude, float longitude){
        return "SRID=4326;POINT(" + String.valueOf(longitude) + " " + String.valueOf(latitude) + ")";
    }
}