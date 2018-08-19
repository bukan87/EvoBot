package ru.evotor.bot.types.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author a.ilyin
 */
public class UserInfo extends AbstractEntity {
    private static final long serialVersionUID = 5077478884903051416L;

    @JsonProperty("is_bot")
    private boolean isBot;

    @JsonProperty("language_code")
    private String languageCode;

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}