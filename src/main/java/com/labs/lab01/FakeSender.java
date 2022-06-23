package com.labs.lab01;

import com.labs.lab01.interfaces.Sender;

public class FakeSender implements Sender {
    @Override
    public void sendMessage(byte[] message) {
        System.out.println(new String(message));
    }

}
