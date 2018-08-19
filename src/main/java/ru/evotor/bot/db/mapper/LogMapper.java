package ru.evotor.bot.db.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author a.ilyin
 */
@Component
public interface LogMapper {

    @Insert("insert into bt_log(is_inline, message, user_id, longitude, latitude)values(#{isInline}, #{message}, #{userId}, #{longitude}, #{latitude})")
    void insertLog(@Param("isInline") String isInline,
                   @Param("message") String message,
                   @Param("userId") Integer userId,
                   @Param("longitude") String longitude,
                   @Param("latitude") String latitude);

    @Select("select\n" +
            "  'SRID=4326;POINT(' || longitude || ' ' || latitude || ')' as position\n" +
            "from\n" +
            "  bt_log\n" +
            "where (user_id, log_date) in (\n" +
            "select\n" +
            "  user_id\n" +
            ", max(log_date)\n" +
            "from\n" +
            "  bt_log\n" +
            "where user_id = #{userId}\n" +
            "  and longitude is not null\n" +
            "  and latitude is not null\n" +
            "group by user_id)")
    String getLastPosition(@Param("userId") Integer userId);
}