package ru.evotor.bot.db.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;
import ru.evotor.bot.db.entity.User;
import ru.evotor.bot.utils.type_handler.LocalDateTimeHandler;

import java.time.LocalDateTime;

/**
 * @author a.ilyin
 */
@Component
public interface UserMapper {

    @Select("select\n" +
            "  user_id,\n" +
            "  last_latitude,\n" +
            "  last_longitude,\n" +
            "  last_location_date,\n" +
            "  last_request,\n" +
            "  last_request_date\n" +
            "from\n" +
            "  bt_users\n"+
            "where user_id = #{userId}")
    @Results(id = "getUserResult", value = {
            @Result(id = true, property = "userId", column = "user_id", javaType = Integer.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "lastLatitude", column = "last_latitude", javaType = Float.class, jdbcType = JdbcType.FLOAT),
            @Result(property = "lastLongitude", column = "last_longitude", javaType = Float.class, jdbcType = JdbcType.FLOAT),
            @Result(property = "lastLocationDate", column = "last_location_date", javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP, typeHandler = LocalDateTimeHandler.class),
            @Result(property = "lastRequest", column = "last_request", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "lastRequestDate", column = "last_request_date", javaType = LocalDateTime.class, jdbcType = JdbcType.TIMESTAMP, typeHandler = LocalDateTimeHandler.class),
    })
    User getUser(@Param("userId") int userId);

    @Insert("insert into bt_users(user_id) values (#{userId})")
    void addUser(@Param("userId") int userId);

    @Update("update bt_users\n" +
            "   set last_latitude = #{lastLatitude}\n" +
            "     , last_longitude = #{lastLongitude}\n" +
            "     , last_location_date = current_timestamp\n" +
            "where user_id = #{userId}")
    void setLocation(@Param("userId") int userId,
                     @Param("lastLatitude") float lastLatitude,
                     @Param("lastLongitude") float lastLongitude);


    @Update("update bt_users\n" +
            "   set last_request = #{request}\n" +
            "     , last_request_date = current_timestamp\n" +
            "where user_id = #{userId}")
    void setRequest(@Param("userId") int userId,
                    @Param("request") String request);
}