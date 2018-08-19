package ru.evotor.bot.types.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.evotor.bot.utils.LocalDateTimeDeserializer;
import ru.evotor.bot.utils.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author a.ilyin
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 137130525282278395L;
    @JsonProperty("message_id")
    private long messageId;
    private UserInfo from;
    private ChatInfo chat;
    @JsonProperty(value = "date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime messageDate;
    private String text;
    @JsonProperty("reply_to_message")
    private Message replyToMessage;
    private Location location;

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public UserInfo getFrom() {
        return from;
    }

    public void setFrom(UserInfo from) {
        this.from = from;
    }

    public ChatInfo getChat() {
        return chat;
    }

    public void setChat(ChatInfo chat) {
        this.chat = chat;
    }

    public LocalDateTime getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(LocalDateTime messageDate) {
        this.messageDate = messageDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Message getReplyToMessage() {
        return replyToMessage;
    }

    public void setReplyToMessage(Message replyToMessage) {
        this.replyToMessage = replyToMessage;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}