package ru.evotor.bot.types.request.markup;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author a.ilyin
 */
public class KeyboardMarkup extends AbstractMarkup {
    private static final long serialVersionUID = 1811031581074307005L;
    private List<List<KeyboardButton>> keyboard;
    @JsonProperty("resize_keyboard")
    private Boolean resizeKeyboard;
    @JsonProperty("one_time_keyboard")
    private Boolean oneTimeKeyboard;
    private Boolean selective;

    public List<List<KeyboardButton>> getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(List<List<KeyboardButton>> keyboard) {
        this.keyboard = keyboard;
    }

    public Boolean getResizeKeyboard() {
        return resizeKeyboard;
    }

    public void setResizeKeyboard(Boolean resizeKeyboard) {
        this.resizeKeyboard = resizeKeyboard;
    }

    public Boolean getOneTimeKeyboard() {
        return oneTimeKeyboard;
    }

    public void setOneTimeKeyboard(Boolean oneTimeKeyboard) {
        this.oneTimeKeyboard = oneTimeKeyboard;
    }

    public Boolean getSelective() {
        return selective;
    }

    public void setSelective(Boolean selective) {
        this.selective = selective;
    }
}