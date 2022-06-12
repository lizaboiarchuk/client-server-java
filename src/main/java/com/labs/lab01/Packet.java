package com.labs.lab01;

public class Packet {

    private final byte client_id;
    private final long packet_id;
    private final Message message;

    public Packet(final byte client, final long packet, final Message message) {
        this.client_id = client;
        this.packet_id = packet;
        this.message = message;
    }

    // getters
    public byte getClientId() { return this.client_id; }
    public long getPacketId() { return this.packet_id; }
    public Message getMessage() { return this.message; }

    @Override
    public String toString() {
        return "Packet:\n" + "Client Id - " + this.client_id + "\nPacket Id - " + this.packet_id + "\nMessage - " + this.message + '\n';
    }
}
