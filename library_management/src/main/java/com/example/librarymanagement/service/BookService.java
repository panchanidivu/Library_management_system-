package com.example.librarymanagement.service;

import java.util.List;

import com.example.librarymanagement.responseDTO.BookCreateDTO;
import com.example.librarymanagement.responseDTO.BookResponseDTO;

public interface BookService {

    List<BookResponseDTO> getAllBooks();

    BookCreateDTO addBook(BookCreateDTO bookCreateDTO);

    BookResponseDTO updateBook(Long id, BookCreateDTO bookDetails);

    Boolean deleteBook(Long id);

    BookResponseDTO getBookById(Long id);
    
}

