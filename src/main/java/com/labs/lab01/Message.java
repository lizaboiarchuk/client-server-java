package com.labs.lab01;

import java.nio.charset.StandardCharsets;

public class Message {

    private final byte[] content;
    private final int cType;
    private final int bUserId;



    public Message(final byte[] content, final int cType, final int bUserId) {
        this.content = content;
        this.cType = cType;
        this.bUserId = bUserId;
    }

    public Message(final String content, final int cType, final int bUserId) {
        this.content = content.getBytes(StandardCharsets.UTF_8);
        this.cType = cType;
        this.bUserId = bUserId;
    }

    // getters
    public byte[] getMessageContent() {
        return content;
    }
    public String getMessageString() { return new String(this.content); }
    public int getcType() { return cType; }
    public int getbUserId() { return bUserId; }



    @Override
    public String toString() {
        return "Message:" + " cType - " + getcType() + "; bUserId - " + getbUserId() + "; Content - " + getMessageString() + ';';
    }
}
