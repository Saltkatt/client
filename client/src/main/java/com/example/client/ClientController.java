package com.example.client;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.Scanner;

public class ClientController {

    private Scanner sc;

    //give user choices
    //1 go to return-service
    //2 go to default message

    public void run(){

        while(true){
            //printMenu();

            String userInput = sc.nextLine();

            switch(userInput) {
                case "1":

            }
        }


    }


}
