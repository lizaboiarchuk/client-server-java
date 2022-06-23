package com.labs.lab01;

import com.labs.lab01.interfaces.Processor;

import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FakeProcessor extends Thread implements Processor {

    public static Queue<Packet> pros_queue;

    enum Responce { OK, NOT_OK }

    public FakeProcessor(){
        super("Processor");
        pros_queue=new ConcurrentLinkedDeque<>();
        start();
    }

    public static void pross_accept(Packet p){
        pros_queue.add(p);
    }


    private Responce get_product_total_number(Message message) {
        System.out.println("GETTING PRODUCT TOTAL NUMBER...");
        return Responce.OK;
    }

    private Responce subtract_product(Message message) {
        System.out.println("SUBTRACTING PRODUCT...");
        return Responce.OK;
    }
    private Responce add_product(Message message) {
        System.out.println("ADDING PRODUCT...");
        return Responce.OK;
    }
    private Responce add_product_group(Message message) {
        System.out.println("ADDING PRODUCT GROUP...");
        return Responce.OK;
    }
    private Responce add_title_to_product_group(Message message) {
        System.out.println("ADDING TITLE TO PRODUCT GROUP...");
        return Responce.OK;
    }
    private Responce set_product_price(Message message) {
        System.out.println("SETTING PRODUCT PRICE...");
        return Responce.OK;
    }

    @Override
    public byte[] process(Message message) {

        int cType = message.getcType();
        Message.cType commandName = Message.cType.values()[cType];
        Responce responce = Responce.NOT_OK;

        switch (commandName) {
            case GET_PRODUCT_TOTAL_NUMBER: responce = get_product_total_number(message); break;
            case SUBTRACT_PRODUCT: responce = subtract_product(message); break;
            case ADD_PRODUCT: responce = add_product(message); break;
            case ADD_PRODUCT_GROUP: responce = add_product_group(message); break;
            case ADD_TITLE_TO_PRODUCT_GROUP: responce = add_title_to_product_group(message); break;
            case SET_PRODUCT_PRICE: responce = set_product_price(message); break;
            default: System.out.println("Undefined command");
        }
        return responce.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void run(){
        while (true){
            try{
                Packet pak= pros_queue.poll();
                if (pak!=null) {

                    //  Thread.sleep(new Random().nextInt(1000));
                    MessageEncryptor.encrypt_accept(new Packet(pak.getClientId(), pak.getPacketId(),
                            new Message("OKKKKKK".getBytes(StandardCharsets.UTF_8),
                                        pak.getMessage().getcType(),
                                        pak.getMessage().getbUserId())));

                    System.out.println("!Server prossesed: " + pak.getMessage().toString());
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }



}
