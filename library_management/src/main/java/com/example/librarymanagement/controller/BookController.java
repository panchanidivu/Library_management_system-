package com.example.librarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.librarymanagement.responseDTO.BookCreateDTO;
import com.example.librarymanagement.responseDTO.BookResponseDTO;
import com.example.librarymanagement.responseDTO.CustomResponseEntity;
import com.example.librarymanagement.responseDTO.CustomResponseStatus;
import com.example.librarymanagement.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/books")
@SecurityRequirement(name = "basicAuth")  

public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // get all books from library
    @Operation(summary = "Get All Books", description = "Get  list of all books in the library", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully get the list of books", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseEntity.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomResponseEntity getAllBooks() {
        return CustomResponseEntity.builder().code(HttpStatus.OK.value())
                .status(CustomResponseStatus.SUCCESS.getStatus()).message(CustomResponseStatus.SUCCESS.getMessage())
                .data(bookService.getAllBooks()).build();
    }

    // add book in library
    @Operation(summary = "Create new Book", description = "add  new book and returns that created book", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Book parameter ", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookCreateDTO.class))), responses = {
            @ApiResponse(responseCode = "201", description = "Successfully created the book", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json"))
    })
    @PostMapping(value = "/createBook", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponseEntity> addBook(@Valid @RequestBody BookCreateDTO bookCreateDTO) {
        BookCreateDTO newBook = bookService.addBook(bookCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(CustomResponseEntity.builder()
                .code(HttpStatus.CREATED.value())
                .status(CustomResponseStatus.SUCCESS.getStatus())
                .message("Book created successfully")
                .data(newBook)
                .build());
    }

    // update book by id
    @Operation(summary = "Update Book by ID", description = "Updates an existing book based on the given Id and book parameter.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated book details to be applied", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookCreateDTO.class))), responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the book", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseEntity.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json"))
    })
    @PutMapping(value = "/updateBook/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponseEntity> updateBook(@PathVariable Long id,
            @Valid @RequestBody BookCreateDTO bookDetails) {
        BookResponseDTO updatedBook = bookService.updateBook(id, bookDetails);
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponseEntity.builder()
                .code(HttpStatus.OK.value())
                .status(CustomResponseStatus.SUCCESS.getStatus())
                .message("Book Update successfully")
                .data(updatedBook)
                .build());
    }

    // Delete book by id
    @Operation(summary = "Delete  Book by Id", description = "Delete book", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully delete book", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseEntity.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/deleteBook/{id}")
    public CustomResponseEntity deleteBook(@PathVariable Long id) {
        Boolean a = bookService.deleteBook(id);
        return CustomResponseEntity.builder()
                .code(HttpStatus.OK.value())
                .status(CustomResponseStatus.SUCCESS.getStatus())
                .message("Books delete successfully")
                .data(a)
                .build();
    }

    // get book by id
    @Operation(summary = "Get Book by Id", description = "get the book base on  given book Id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully get book details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomResponseEntity.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/getBookById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponseEntity> getBookByid(@PathVariable Long id) {
        BookResponseDTO bookResponseDTO = bookService.getBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body(CustomResponseEntity.builder()
                .code(HttpStatus.OK.value())
                .status(CustomResponseStatus.SUCCESS.getStatus())
                .message("success")
                .data(bookResponseDTO)
                .build());

    }

}
