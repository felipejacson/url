package com.url.util.messages;

import com.url.util.constants.MessageTypeEnum;

public class Message {

    private MessageTypeEnum type;
    private String message;

    public Message(MessageTypeEnum type, String message) {
        this.type = type;
        this.message = message;
    }

    public MessageTypeEnum getType() {
        return type;
    }

    public void setType(MessageTypeEnum type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
