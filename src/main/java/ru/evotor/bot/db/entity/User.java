package ru.evotor.bot.db.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author a.ilyin
 */
public class User implements Serializable {

    private static final long serialVersionUID = 8666476738693786188L;
    private int userId;
    private Float lastLongitude;
    private Float lastLatitude;
    private LocalDateTime lastLocationDate;
    private String lastRequest;
    private LocalDateTime lastRequestDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Float getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(Float lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public Float getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(Float lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public LocalDateTime getLastLocationDate() {
        return lastLocationDate;
    }

    public void setLastLocationDate(LocalDateTime lastLocationDate) {
        this.lastLocationDate = lastLocationDate;
    }

    public String getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(String lastRequest) {
        this.lastRequest = lastRequest;
    }

    public LocalDateTime getLastRequestDate() {
        return lastRequestDate;
    }

    public void setLastRequestDate(LocalDateTime lastRequestDate) {
        this.lastRequestDate = lastRequestDate;
    }
}