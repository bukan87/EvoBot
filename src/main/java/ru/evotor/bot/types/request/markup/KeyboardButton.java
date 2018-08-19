package ru.evotor.bot.types.request.markup;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author a.ilyin
 */
public class KeyboardButton implements Serializable {
    private static final long serialVersionUID = -3390001714945057976L;
    private String text;
    @JsonProperty("request_contact")
    private Boolean requestContact;
    @JsonProperty("request_location")
    private Boolean requestLocation;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getRequestContact() {
        return requestContact;
    }

    public void setRequestContact(Boolean requestContact) {
        this.requestContact = requestContact;
    }

    public Boolean getRequestLocation() {
        return requestLocation;
    }

    public void setRequestLocation(Boolean requestLocation) {
        this.requestLocation = requestLocation;
    }
}