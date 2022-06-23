package com.labs.lab01;

import com.labs.lab01.interfaces.Receiver;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FakeReceiver extends Thread implements Receiver {

    private RGenerator generator = new RGenerator();
    MessageEncryptor coder = new MessageEncryptor();

    public FakeReceiver() throws NoSuchPaddingException, NoSuchAlgorithmException {
        super("ReceiverThread");
        start();
    }

    @Override
    public byte[] receiveMessage() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Packet packet = generator.generatePacket();
        return coder.encode(packet);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Packet packet = generator.generatePacket();
                coder.encode(packet);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
