package com.labs.lab01.interfaces;

import com.labs.lab01.Message;
import com.labs.lab01.Packet;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;

public interface Encryptor {
    public byte[] encode(Packet packet) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException;
}
