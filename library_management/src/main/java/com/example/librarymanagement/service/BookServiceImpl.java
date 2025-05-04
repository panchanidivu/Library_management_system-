package com.example.librarymanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.librarymanagement.exceptions.BookNotFoundException;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.responseDTO.BookCreateDTO;
import com.example.librarymanagement.responseDTO.BookResponseDTO;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        logger.info("Get all books");

        List<Book> books = bookRepository.findAll();
        List<BookResponseDTO> bookResponseDTOs = new ArrayList<>();
        if (books.isEmpty()) {
            logger.warn("books not found");
        }
        for (Book book : books) {
            BookResponseDTO bookResponseDTO = new BookResponseDTO();
            bookResponseDTO.setId(book.getId());
            bookResponseDTO.setTitle(book.getTitle());
            bookResponseDTO.setAuthorName(book.getAuthor());
            bookResponseDTO.setPublicationYear(book.getPublicationYear());

            bookResponseDTOs.add(bookResponseDTO);
        }
        return bookResponseDTOs;
    }

    @Override
    public BookCreateDTO addBook(BookCreateDTO bookCreateDTO) {
        logger.info("Create new book: {}", bookCreateDTO.getTitle());

        Book newBook = new Book();
        newBook.setTitle(bookCreateDTO.getTitle());
        newBook.setAuthor(bookCreateDTO.getAuthor());
        newBook.setPublicationYear(bookCreateDTO.getPublicationYear());
        Book savedBook = bookRepository.save(newBook);
        logger.info("Book create with Id: {}", savedBook.getId());

        return new BookCreateDTO(savedBook.getTitle(), savedBook.getAuthor(), savedBook.getPublicationYear());
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookCreateDTO bookDetails) {
        logger.info("Update book Id: {}", id);

        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            logger.error("Book Id {} not found", id);

            throw new BookNotFoundException("Book Id " + id + " not found");
        }
        book.ifPresent(bookUpdate -> {
            bookUpdate.setTitle(bookDetails.getTitle());
            bookUpdate.setAuthor(bookDetails.getAuthor());
            bookUpdate.setPublicationYear(bookDetails.getPublicationYear());

            bookRepository.save(bookUpdate);
            logger.info("Book Id {} successfully update.", id);

        });

        return new BookResponseDTO(
                book.get().getId(),
                book.get().getTitle(),
                book.get().getAuthor(),
                book.get().getPublicationYear());
    }

    @Override
    public Boolean deleteBook(Long id) {
        Optional<Book> deleteBook = bookRepository.findById(id);
        if (!deleteBook.isPresent()) {
            throw new BookNotFoundException("Book Id " + id + " not found");
        }
        bookRepository.delete(deleteBook.get());
        return true;
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        logger.info("get book by Id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book Id " + id + " not found"));

        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear());
    }

}
