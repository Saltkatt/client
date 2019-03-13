package com.example.client;


public class ClientApplication {


    public static void main(String[] args) {
        ClientController controller = new ClientController(new ContactService());
        controller.run();
    }

}
