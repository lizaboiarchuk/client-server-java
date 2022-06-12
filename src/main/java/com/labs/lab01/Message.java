package com.labs.lab01;

import java.nio.charset.StandardCharsets;

public class Message {

    private final byte[] content;

    public Message(final byte[] content) {
        this.content = content;
    }

    public Message(final String content) {
        this.content = content.getBytes(StandardCharsets.UTF_8);
    }

    // getters
    public byte[] getMessageContent() {
        return content;
    }
    public String getMessageString() { return new String(this.content); }

    @Override
    public String toString() {
        return getMessageString();
    }
}
