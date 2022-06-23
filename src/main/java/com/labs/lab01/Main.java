package com.labs.lab01;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class Main
{
    public static void main( String[] args ) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {

        FakeReceiver receiver = new FakeReceiver();
        MessageDecryptor coder = new MessageDecryptor();
        FakeProcessor processor = new FakeProcessor();
        FakeSender sender = new FakeSender();

        byte[] bytes = receiver.receiveMessage();
        Packet packet = coder.decode(bytes);
        byte[] result = processor.process(packet.getMessage());
        sender.sendMessage(result);
    }
}
