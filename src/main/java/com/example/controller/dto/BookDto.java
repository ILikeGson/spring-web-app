package com.example.controller.dto;

import com.example.model.Author;
import com.example.model.Book;
import com.example.model.BookComment;
import com.example.model.Genre;

import java.util.List;

public class BookDto {
    private long id;
    private String title;
    private Author author;
    private Genre genre;
    private List<BookComment> comments;

    public BookDto(long id, String title, Author author, Genre genre, List<BookComment> comments) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }

    public BookDto(String title, Author author, Genre genre, List<BookComment> comments) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }

    public BookDto() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<BookComment> getComments() {
        return comments;
    }

    public void setComments(List<BookComment> comments) {
        this.comments = comments;
    }

    public static Book toDomainObject(BookDto dto) {
        Book book = new Book(dto.getTitle(), dto.getAuthor(), dto.getGenre(), dto.getComments());
        book.setId(dto.getId());
        return book;
    }

    public static BookDto toBookDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getComments());
    }
}
