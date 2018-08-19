package ru.evotor.bot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.evotor.bot.types.Proxy;

/**
 * @author a.ilyin
 */
@Component
@ConfigurationProperties(prefix = "evotor.bot")
public class Property {
    private int maxDistance = 30000;
    private int maxShops = 3;
    private String botName;
    private String botToken;
    private Proxy proxy;

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public int getMaxShops() {
        return maxShops;
    }

    public void setMaxShops(int maxShops) {
        this.maxShops = maxShops;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }
}