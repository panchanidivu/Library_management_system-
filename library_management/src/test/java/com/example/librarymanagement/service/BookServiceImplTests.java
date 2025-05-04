package com.example.librarymanagement.service;

import com.example.librarymanagement.exceptions.BookNotFoundException;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.responseDTO.BookCreateDTO;
import com.example.librarymanagement.responseDTO.BookResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceImplTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublicationYear(2021);
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        var books = bookService.getAllBooks();
        assertEquals(1, books.size());
        assertEquals("Test Book", books.get(0).getTitle());
    }

    @Test
    void testAddBook() {
        BookCreateDTO bookCreateDTO = new BookCreateDTO("New Book", "New Author", 2022);
        Book savedBook = new Book();
        savedBook.setId(2L);
        savedBook.setTitle(bookCreateDTO.getTitle());
        savedBook.setAuthor(bookCreateDTO.getAuthor());
        savedBook.setPublicationYear(bookCreateDTO.getPublicationYear());

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(savedBook);

        BookCreateDTO result = bookService.addBook(bookCreateDTO);
        assertEquals("New Book", result.getTitle());
        assertEquals("New Author", result.getAuthor());
    }

    @Test
    void testUpdateBook() {
        BookCreateDTO bookCreateDTO = new BookCreateDTO("Updated Book", "Updated Author", 2023);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        BookResponseDTO updatedBook = bookService.updateBook(1L, bookCreateDTO);
        assertEquals("Updated Book", updatedBook.getTitle());
        assertEquals("Updated Author", updatedBook.getAuthorName());
    }

    @Test
    void testUpdateBook_BookNotFound() {
        BookCreateDTO bookCreateDTO = new BookCreateDTO("Updated Book", "Updated Author", 2023);

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1L, bookCreateDTO));
    }

    @Test
    void testDeleteBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        boolean result = bookService.deleteBook(1L);
        assertTrue(result);
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void testDeleteBook_BookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
    }

    @Test
    void testGetBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookResponseDTO result = bookService.getBookById(1L);
        assertEquals("Test Book", result.getTitle());
    }

    @Test
    void testGetBookById_BookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
    }
}
