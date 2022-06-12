package com.labs.lab01;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class MessageCoder {

    private static final String ENCRYPTION_STRING_KEY = "encryptkeystring";
    private static final byte START_BYTE = 0x13;
    private static int minMessageLength = 8;
    private static SecretKey secret;
    private static Cipher cipher;


    public MessageCoder() throws NoSuchPaddingException, NoSuchAlgorithmException {
        byte[] encryptionBytes = ENCRYPTION_STRING_KEY.getBytes();
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        secret = new SecretKeySpec(encryptionBytes, "AES");
    }


    public static byte[] encode(Packet packet) throws BadPaddingException,IllegalBlockSizeException, InvalidKeyException {

        Message message = packet.getMessage();

        // encrypt message content
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] contentEncrypted = cipher.doFinal(message.getMessageContent());

        // create packet
        byte[] head = ByteBuffer.allocate(14)
                .order(ByteOrder.BIG_ENDIAN)
                .put(START_BYTE)
                .put(packet.getClientId())
                .putLong(packet.getPacketId())
                .putInt(contentEncrypted.length)
                .array();

        byte[] body = ByteBuffer.allocate(contentEncrypted.length)
                .order(ByteOrder.BIG_ENDIAN)
                .put(contentEncrypted)
                .array();

        return ByteBuffer.allocate(18 + minMessageLength + contentEncrypted.length)
                .order(ByteOrder.BIG_ENDIAN)
                .put(head)
                .putShort(CRC16.crc16(head))
                .put(body)
                .putShort(CRC16.crc16(body))
                .array();
    }


    public static Packet decode(byte[] bytes) throws  BadPaddingException,  IllegalBlockSizeException, InvalidKeyException {

        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
        if (buffer.get() != START_BYTE) { throw new IllegalArgumentException("Invalid start byte :( "); }

        byte clientId = buffer.get();
        long packetId = buffer.getLong();
        int contentLen = buffer.getInt();
        short cHead = buffer.getShort();

        // check packet head for validity
        byte [] head = ByteBuffer.allocate(14)
                .order(ByteOrder.BIG_ENDIAN)
                .put(START_BYTE)
                .put(clientId)
                .putLong(packetId)
                .putInt(contentLen)
                .array();

        if (CRC16.crc16(head)!= cHead) { throw new IllegalArgumentException("CRC16 head broken :( "); }

        // check packet body for validity
        byte [] contentBytes = Arrays.copyOfRange(bytes, 16, 16 + contentLen);
        short cContent = buffer.getShort(16 + contentLen);
        if (CRC16.crc16(contentBytes) != cContent) { throw new IllegalArgumentException("CRC16 body broken :( "); }

        // decrypt message content
        cipher.init(Cipher.DECRYPT_MODE, secret);
        byte[] decodedContent = cipher.doFinal(contentBytes);

        return new Packet(clientId, packetId, new Message(decodedContent));
    }


}
