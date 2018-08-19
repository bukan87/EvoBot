package ru.evotor.bot.types.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.evotor.bot.types.request.markup.AbstractMarkup;

import java.io.Serializable;

/**
 * @author a.ilyin
 */
public class RequestMessage implements Serializable {
    private static final long serialVersionUID = -6282320038505569276L;
    @JsonProperty("chat_id")
    private long chatId;
    private String text;
    @JsonProperty("reply_to_message_id")
    private long replyToMessageId;
    @JsonProperty("reply_markup")
    private AbstractMarkup markup;

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getReplyToMessageId() {
        return replyToMessageId;
    }

    public void setReplyToMessageId(long replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }

    public AbstractMarkup getMarkup() {
        return markup;
    }

    public void setMarkup(AbstractMarkup markup) {
        this.markup = markup;
    }
}