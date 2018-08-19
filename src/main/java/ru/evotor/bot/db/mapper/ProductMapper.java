package ru.evotor.bot.db.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;
import ru.evotor.bot.db.entity.ProductByName;
import ru.evotor.bot.db.entity.ProductInNearestShop;

import java.util.List;

/**
 * @author a.ilyin
 */
@Component
public interface ProductMapper {

    @Select("select\n" +
            "  initcap(regexp_replace(str, '\\W+', '')) as found\n" +
            ", max(name) as product_name\n" +
            ", min(price) as min_price\n" +
            ", max(price) as max_price\n" +
            "from\n" +
            "  (\n" +
            "    select\n" +
            "      p.*,\n" +
            "      lower(regexp_split_to_table(name, '\\m')) as str\n" +
            "    from\n" +
            "      bt_products p\n" +
            "    where lower(name) ~ ('(^|\\s)' || #{word})\n" +
            "  ) t\n" +
            "where str like #{word} || '%'\n" +
            "group by\n" +
            "  1\n" +
            "order by\n" +
            "  1")
    @Results(id = "getProductsResult", value = {
            @Result(id = true, property = "foundWord", column = "found", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "productName", column = "product_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "minPrice", column = "min_price", javaType = Double.class, jdbcType = JdbcType.NUMERIC),
            @Result(property = "maxPrice", column = "max_price", javaType = Double.class, jdbcType = JdbcType.NUMERIC)
    })
    List<ProductByName> getProducts(@Param("word") String word);


    @Select("select\n" +
            "  sh.id,\n" +
            "  max(sh.name) as shopName,\n" +
            "  max(sh.address) as address,\n" +
            "  max(point <-> st_geogfromtext(#{location})) distance,\n" +
            "  max(sh.lng) as longitude,\n" +
            "  max(sh.lat) as latitude,\n" +
            "  max(pr.name) as product_name,\n" +
            "  max(pr.price) as price\n" +
            "from\n" +
            "  bt_shops sh\n" +
            "  join bt_products pr on pr.shop_id = sh.shop_id\n" +
            "where 1 = 1\n" +
            "  and to_tsvector('russian' :: regconfig, pr.name :: text) @@ plainto_tsquery(#{productName} || ':*')\n" +
            "  and ST_DWithin(\n" +
            "          sh.point,\n" +
            "          st_geogfromtext(#{location}),\n" +
            "          #{maxDistance})\n" +
            "group by\n" +
            "  sh.id\n" +
            "order by\n" +
            "  distance\n" +
            "limit #{maxRows}")
    @Results(id = "getProductsInNearestShopsResult", value = {
            @Result(id = true, property = "shopId", column = "id", javaType = Integer.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "shopName", column = "shopName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "distance", column = "distance", javaType = Double.class, jdbcType = JdbcType.NUMERIC),
            @Result(property = "longitude", column = "longitude", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "latitude", column = "latitude", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "productName", column = "product_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "price", column = "price", javaType = Double.class, jdbcType = JdbcType.NUMERIC),
    })
    List<ProductInNearestShop> getProductsInNearestShops(@Param("productName") String productName,
                                                         @Param("location") String location,
                                                         @Param("maxDistance") int maxDistance,
                                                         @Param("maxRows") int maxRows);
}