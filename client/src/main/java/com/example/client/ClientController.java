package com.example.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ClientController {

    private ContactService conService;
    private Scanner sc;

    public ClientController(ContactService conService) {
        this.conService = conService;
        sc = new Scanner(System.in);
    }

    public void run(){

        while(true){
            System.out.println("Welcome to the Library! \nFeel free to look around.");
            mainMenu();

            String choice = sc.nextLine();

            switch(choice) {
                case "1": addBook(); break;
                case "2": deleteBook(); break;
                case "3": getAllBooks(); break;
                case "4": getBookById(); break;
                case "5": getBookByTitle(); break;
                case "6": updateBook(); break;
                case "7": System.exit(0);
                default:
                    System.out.println("Input is not recognised, please try again.");
            }
            System.out.println("\n");
        }

    }

    private void mainMenu(){
        List<String> menuOptions = new ArrayList<>();
        Method[] methods = ContactService.class.getDeclaredMethods();
        for(Method m: methods){
            menuOptions.add(m.getName());
        }
        Collections.sort(menuOptions);
        menuOptions.add("exit library");
        int i = 1;
        for(String s: menuOptions){
            System.out.println(i++ + ") " + s);
        }
    }

    public void getAllBooks(){
        String inventory = conService.getAllBooks();

        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<Book> library = null;
        try {
            library = objectMapper.readValue(inventory, typeFactory.constructCollectionType(List.class, Book.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Book book: library){
            System.out.println(book);
        }

    }

    public void getBookById(){
        System.out.println("Enter book ID: ");
        String bookId = sc.nextLine();

        System.out.println(conService.getBookById(bookId));

    }

    public void getBookByTitle(){
        System.out.println("Enter book title: ");
        String bookName = sc.nextLine();

        System.out.println(conService.getBookByTitle(bookName));

    }

    public void addBook(){

        System.out.println("Add title of book:");
        String bookName = sc.nextLine();

        System.out.println("Add description of book:");
        String description = sc.nextLine();

        conService.addBook(bookName, description);
    }

    public void updateBook(){
        System.out.println("To update book title and description first enter book ID: ");
        String bookId = sc.nextLine();

        System.out.println("Update book title: ");
        String newBookName = sc.nextLine();

        System.out.println("Update description: ");
        String newDescription = sc.nextLine();

        conService.updateBook(bookId, newBookName, newDescription);

    }

    public void deleteBook(){

        System.out.println("Enter the ID of the book you wish to remove: ");
        String bookId = sc.nextLine();

        conService.deleteBook(bookId);
    }


}
