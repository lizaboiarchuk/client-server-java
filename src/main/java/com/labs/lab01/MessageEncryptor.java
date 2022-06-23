package com.labs.lab01;

import com.labs.lab01.interfaces.Encryptor;

import javax.crypto.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;


public class MessageEncryptor extends Thread implements Encryptor {

    public static Queue<Packet> encryptor_que;

    private static final String ENCRYPTION_STRING_KEY = "encryptkeystring";
    private static final byte START_BYTE = 0x13;
    private static int minMessageLength = 8;
    private static SecretKey secret;
    private static Cipher cipher;


    public static void encrypt_accept(Packet p){
        encryptor_que.add(p);
    }

    public MessageEncryptor(){
        super("Encryptor");
        encryptor_que = new ConcurrentLinkedDeque<>();
        start();
    }


    public static byte[] encryptMessage(byte[] bytes) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        return cipher.doFinal(bytes);
    }


    public byte[] encode(Packet packet) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

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
                .putInt(minMessageLength + contentEncrypted.length)
                .array();

        byte[] body = ByteBuffer.allocate(minMessageLength + contentEncrypted.length)
                .order(ByteOrder.BIG_ENDIAN)
                .putInt(message.getcType())
                .putInt(message.getbUserId())
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
}
