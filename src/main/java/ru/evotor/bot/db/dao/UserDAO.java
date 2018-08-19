package ru.evotor.bot.db.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.evotor.bot.db.entity.User;
import ru.evotor.bot.db.mapper.UserMapper;

/**
 * @author a.ilyin
 */
@Repository
public class UserDAO {

    private final UserMapper userMapper;

    public UserDAO(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Transactional
    public User getUser(int userId){
        User user = userMapper.getUser(userId);
        if (user == null){
            userMapper.addUser(userId);
            user = new User();
            user.setUserId(userId);
        }
        return user;
    }

    @Transactional
    public void setLocation(int userId, float lastLatitude, float lastLongitude){
        getUser(userId);
        userMapper.setLocation(userId, lastLatitude, lastLongitude);
    }

    @Transactional
    public void setRequest(int userId, String request){
        getUser(userId);
        userMapper.setRequest(userId, request);
    }
}