package ru.evotor.bot.types.response;

/**
 * @author a.ilyin
 */
public class ChatInfo extends AbstractEntity {
    private static final long serialVersionUID = -8836245840451380852L;
    // TODO переделать на ENUM
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}