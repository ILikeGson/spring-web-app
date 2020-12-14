package com.example.service;

import com.example.model.Author;
import com.example.model.Book;
import com.example.model.BookComment;
import com.example.model.Genre;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.List;
import java.util.Set;

@Service
public class LibraryServiceImpl implements LibraryService{

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookCommentService commentService;
    private final GenreService genreService;

    public LibraryServiceImpl(BookService bookService, AuthorService authorService,
                              BookCommentService commentService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.commentService = commentService;
        this.genreService = genreService;
    }

    @Transactional
    @Override
    public Book saveBook(Book book) {
        Optional<Genre> foundGenre = genreService.findByGenre((book.getGenre().getGenreName()));
        if (foundGenre.isPresent()) {
            book.setGenre(foundGenre.get());
        } else {
            book.setGenre(genreService.save(book.getGenre()));
        }
        Optional<Author> foundAuthor = authorService.findByFirstNameAndLastName(book.getAuthor().getFirstName(), book.getAuthor().getLastName());
        if (foundAuthor.isPresent()) {
            book.setAuthor(foundAuthor.get());
        } else {
            book.setAuthor(authorService.save(book.getAuthor()));
        }
        return bookService.save(book);
    }

    @Transactional
    @Override
    public Author saveAuthor(String firstName, String lastName) {
        return authorService.save(new Author(firstName, lastName));
    }

    @Transactional
    @Override
    public Genre saveGenre(String genre) {
        return genreService.save(new Genre(genre));
    }

    @Transactional
    @Override
    public BookComment saveCommentToBook(String comment, long id) {
        Book book = bookService.findById(id);
        BookComment bookComment = new BookComment(comment);
        bookComment.setBook(book);
        return commentService.save(bookComment);
    }

    @Transactional(readOnly = true)
    @Override
    public Book findBookById(long id) {
       return bookService.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findBooksByTitle(String title) {
       return bookService.findByTitle(title);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Book> findBooksByAuthor(String authorName, String authorLastName) {
        Author author = authorService.findByFirstNameAndLastName(authorName, authorLastName).orElseThrow();
        return author.getBooks();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findBooksByGenre(String genre) {
        return genreService.findByGenre(genre).orElseThrow().getBooks();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAllBooks() {
        return bookService.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> findAllGenres() {
        return genreService.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> findAllAuthors() {
        return authorService.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public long countAllBooks() {
        return bookService.count();
    }

    @Transactional
    @Override
    public void updateBookById(Book book, long id) {
        String authorFirstName = book.getAuthor().getFirstName();
        String authorLastName = book.getAuthor().getLastName();
        Optional<Author> authorOptional = authorService.findByFirstNameAndLastName(authorFirstName, authorLastName);
        Optional<Genre> genreOptional = genreService.findByGenre(book.getGenre().getGenreName());
        Book oldBook = bookService.findById(id);
        oldBook.setTitle(book.getTitle());
        if (authorOptional.isEmpty()) {
            oldBook.setAuthor(authorService.save(new Author(authorFirstName, authorLastName)));
        } else {
            oldBook.setAuthor(authorOptional.get());
        }

        if (genreOptional.isEmpty()) {
            oldBook.setGenre(genreService.save(new Genre(book.getGenre().getGenreName())));
        } else {
            oldBook.setGenre(genreOptional.get());
        }
        bookService.updateById(oldBook, id);
    }

    @Transactional
    @Override
    public void deleteBookById(long id) {
        bookService.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        bookService.deleteAll();
    }
}
