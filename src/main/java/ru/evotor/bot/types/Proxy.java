package ru.evotor.bot.types;

import java.io.Serializable;

/**
 * @author a.ilyin
 */
public class Proxy implements Serializable {
    private static final long serialVersionUID = 6015468905776773290L;

    private String host;
    private int port;
    private String userName;
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}