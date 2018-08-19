package ru.evotor.bot.types.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author a.ilyin
 */
public class UpdateResponse implements Serializable {
    private static final long serialVersionUID = -8342539109387794986L;
    @JsonProperty("ok")
    private boolean isOk;
    private List<Update> updates;

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public List<Update> getUpdates() {
        return updates;
    }

    public void setUpdates(List<Update> updates) {
        this.updates = updates;
    }
}