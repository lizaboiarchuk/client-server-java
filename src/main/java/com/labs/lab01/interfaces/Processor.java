package com.labs.lab01.interfaces;

import com.labs.lab01.FakeProcessor;
import com.labs.lab01.Message;

public interface Processor {

    public byte[] process(Message message);

}
