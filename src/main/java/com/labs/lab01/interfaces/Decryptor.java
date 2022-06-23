package com.labs.lab01.interfaces;

import com.labs.lab01.Packet;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

public interface Decryptor {
    public Packet decode(byte[] message) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException;
}
