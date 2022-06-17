package com.labs.lab01;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class Main
{

    public static void main( String[] args ) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {

        // create message
        String message = "Hey you there";
        Packet packet = new Packet((byte)1,10L, new Message(message, 0, 1));
        System.out.println("Created packet\n" + packet);

        MessageCoder coder = new MessageCoder();

        // encode packet
        byte[] encoded = coder.encode(packet);
        System.out.println("Encoded packet bytes: " + encoded);

        // decode bytes
        Packet decoded = coder.decode(encoded);
        System.out.println("\nDecoded packet\n" + decoded);








    }
}
