package com.example.client;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Book {

    private @Id @GeneratedValue Long bookId;
    private @NonNull String bookName;
    private @NonNull String description;
    private LocalDateTime dateTime;

    public Book() {
    }

    public Book(@NonNull String bookName, @NonNull String description, LocalDateTime dateTime) {
        this.bookName = bookName;
        this.description = description;
        this.dateTime = dateTime;
    }
}
