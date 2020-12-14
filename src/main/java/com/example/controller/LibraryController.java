package com.example.controller;

import com.example.controller.dto.BookDto;
import com.example.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("count", libraryService.countAllBooks());
        return "index";
    }

    @GetMapping("/add")
    public String showPageAddBook(Model model) {
        BookDto dto = new BookDto();
        model.addAttribute("book", dto);
        return "add-book";
    }

    @PostMapping("/add")
    public String savedSuccessfully(@ModelAttribute("book") BookDto dto) {
        libraryService.saveBook(BookDto.toDomainObject(dto));
        return "add-book-successfully";
    }

    @GetMapping("/book-search")
    public String getAll(Model model) {
        List<BookDto> books = libraryService.findAllBooks().stream().map(BookDto::toBookDto).collect(Collectors.toList());
        model.addAttribute("books", books);
        return "search-book";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteById(@RequestParam("id") long id) {
        libraryService.deleteBookById(id);
        return "redirect:/book-search";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        BookDto bookDto = BookDto.toBookDto(libraryService.findBookById(id));
        model.addAttribute("book", bookDto);
        return "edit-book";
    }

    @PostMapping("/edit/{id}")
    public String editBook (@PathVariable("id") long id, BookDto dto) {
        BookDto bookDto = BookDto.toBookDto(libraryService.findBookById(id));
        dto.setComments(bookDto.getComments());
        libraryService.updateBookById(BookDto.toDomainObject(dto), id);
        return "redirect:/book-search";
    }
}
