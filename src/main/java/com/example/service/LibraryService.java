package com.example.service;

import com.example.model.Author;
import com.example.model.Book;
import com.example.model.BookComment;
import com.example.model.Genre;

import java.util.List;
import java.util.Set;

public interface LibraryService {
    Book saveBook(Book book);
    Author saveAuthor(String firstName, String lastName);
    Genre saveGenre(String genre);
    BookComment saveCommentToBook(String comment, long id);
    Book findBookById(long id);
    List<Book> findBooksByTitle(String title);
    Set<Book> findBooksByAuthor(String authorName, String authorLastName);
    List<Book> findBooksByGenre(String genre);
    List<Book> findAllBooks();
    List<Genre> findAllGenres();
    List<Author> findAllAuthors();
    long countAllBooks();
    void updateBookById(Book book, long id);
    void deleteBookById(long id);
    void deleteAll();
}
