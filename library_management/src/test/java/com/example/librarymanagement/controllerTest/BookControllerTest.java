package com.example.librarymanagement.controllerTest;

import com.example.librarymanagement.service.BookService;
import com.example.librarymanagement.controller.BookController;
import com.example.librarymanagement.responseDTO.BookCreateDTO;
import com.example.librarymanagement.responseDTO.BookResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
@ExtendWith(MockitoExtension.class) 

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;
    private BookResponseDTO bookResponseDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        bookResponseDTO = new BookResponseDTO(1L, "Test Book", "Test Author", 2021);
    }

    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(java.util.Collections.singletonList(bookResponseDTO));

        mockMvc.perform(get("/api/books/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data[0].title").value("Test Book"))
                .andExpect(jsonPath("$.data[0].authorName").value("Test Author"))
                .andExpect(jsonPath("$.data[0].publicationYear").value(2021));
    }

    @Test
    void testAddBook() throws Exception {
        BookCreateDTO bookCreateDTO = new BookCreateDTO("New Book", "New Author", 2022);
        BookCreateDTO responseDTO = new BookCreateDTO("New Book", "New Author", 2022);

        when(bookService.addBook(any(BookCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/books/createBook")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(bookCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.data.title").value("New Book"))
                .andExpect(jsonPath("$.data.author").value("New Author"));
    }

    @Test
    void testUpdateBook() throws Exception {
        BookCreateDTO bookCreateDTO = new BookCreateDTO("Updated Book", "Updated Author", 2023);
        when(bookService.updateBook(1L, bookCreateDTO)).thenReturn(bookResponseDTO);

        mockMvc.perform(put("/api/books/updateBook/1")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(bookCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.data.title").value("Test Book"))
                .andExpect(jsonPath("$.data.authorName").value("Test Author"));
    }

    @Test
    void testDeleteBook() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/books/deleteBook/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Books delete successfully"));
    }

    @Test
void testGetBookById() throws Exception {
    when(bookService.getBookById(1L)).thenReturn(bookResponseDTO);

    mockMvc.perform(get("/api/books/getBookById/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.data.title").value("Test Book"))
            .andExpect(jsonPath("$.data.authorName").value("Test Author"))
            .andExpect(jsonPath("$.data.publicationYear").value(2021));
}
}
