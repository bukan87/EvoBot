package ru.evotor.bot.types.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author a.ilyin
 */
public class Update implements Serializable {
    private static final long serialVersionUID = 3558279162967902051L;
    @JsonProperty("update_id")
    private long updateId;
    private Message message;

    public long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(long updateId) {
        this.updateId = updateId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}