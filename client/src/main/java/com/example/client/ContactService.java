package com.example.client;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class ContactService {

    private RestTemplate restTemplate = new RestTemplate();
    //String libraryURL = "http://localhost:8085/library"; //not correct
    String libraryURL = "https://library-service.herokuapp.com/library/";


    public String getAllBooks(){
        return restTemplate.getForObject(libraryURL + "/getBooks", String.class);
    }

    public String getBookById(String bookId){

        try {
            return restTemplate.getForObject(libraryURL + "/getBook/" + bookId, String.class);
        }catch (HttpClientErrorException e){
            System.out.println("Book ID: " + bookId + "\nThis ID is not valid.");
            return "";
        }
    }

    public String getBookByTitle(String bookName){
        try{
            return restTemplate.getForObject(libraryURL + "/getBookTitle/"+ bookName, String.class);
        }catch (HttpClientErrorException e) {
            System.out.println("Book title: " + bookName + "\nThis book title is not valid.");
            return "";
        }
    }

    public void addBook(String bookName, String description){
        Book book = new Book();
        book.setBookName(bookName);
        book.setDescription(description);

        HttpEntity<Book> request = new HttpEntity<>(book);
        try{
            restTemplate.postForObject(libraryURL + "/addBook/", request, Book.class);
        }catch (Exception e){
            System.out.println("This book " + bookName + " cannot be added.");
        }
    }

    public void deleteBook(String bookId) {
        try{
            restTemplate.delete(libraryURL + "/removeBook/" + bookId);
        }catch(Exception e){
            System.out.println("Book ID: " + bookId + "\ncould not be removed.");
        }

    }

    public void updateBook(String bookId, String newBookName, String newDescription){
        String bookInRepo = getBookById(bookId);
        if(bookInRepo.equals("")){
            return;
        }
        Book book = new Book();
        book.setBookName(newBookName);
        book.setDescription(newDescription);

        HttpEntity<Book> request = new HttpEntity<>(book);
        try{
            restTemplate.put(libraryURL + "/updateBook/" + bookId, request, Book.class);
        }catch (Exception e){
            System.out.println("The book " + newBookName + " is already taken.");
        }
    }

}
