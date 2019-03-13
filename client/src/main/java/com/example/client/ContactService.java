package com.example.client;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class ContactService {

    private RestTemplate restTemplate = new RestTemplate();
    String project2Url = "http://localhost:8085/library"; //not correct

    public String getAllBooks(){
        return restTemplate.getForObject(project2Url + "/getBooks", String.class);
    }

    public String getBookById(Long bookID){
        try {
            return restTemplate.getForObject(project2Url + "/getBook/{bookId}", String.class);
        }catch (HttpClientErrorException e){
            System.out.println("Book ID: " + bookID + "\n This ID is not valid.");
            return "";
        }
    }

    public String getBookByTitle(String bookName){
        try{
            return restTemplate.getForObject(project2Url + "getBook/{bookName}", String.class);
        }catch (Exception e) {
            System.out.println("Book title: " + bookName + "\n This book title is not valid.");
            return "";
        }

    }

    public void addBook(String bookName, String description){
        Book book = new Book();
        book.setBookName(bookName);
        book.setDescription(description);

        HttpEntity<Book> request = new HttpEntity<>(book);
        try{
            restTemplate.postForObject(project2Url + "/addBook", request, Book.class);
        }catch (Exception e){
            System.out.println("This book " + bookName + " cannot be added.");
        }
    }

    public void deleteBook(Long bookId) {
        try{
            restTemplate.delete(project2Url + "/removeBook/{bookId}" + bookId);
        }catch(Exception e){
            System.out.println("Book ID" + bookId + "\n could not be removed.");
        }

    }

    public void updateBook(Long bookId, String newBookName, String newDescription) {
        String bookInRepo = getBookById(bookId);
        if (bookInRepo.equals("")) {
            return; //the right error message (getUserById) gets displayed if this check fails as well as returning
        }

        Book book = new Book();
        book.setBookName(newBookName);
        book.setDescription(newDescription);

        HttpEntity<Book> request = new HttpEntity<>(book);
        try {
            restTemplate.put(project2Url + "/updateBook/{bookName}" + newBookName, newDescription, request, Book.class);
        } catch (Exception e) {
            System.out.println("The book " + newBookName + " is already taken.");
        }
    }


}
