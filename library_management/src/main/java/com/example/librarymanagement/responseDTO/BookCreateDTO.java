package com.example.librarymanagement.responseDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateDTO {

    @NotBlank
    @Size(min = 1, message = "Title is required")
    private String title;

    @NotBlank
    @Size(min = 1, message = "Author is required")
    private String author;

    @NotNull(message = "Publication Year is required")
    @Min(value = 1, message = "Publication year must be greater than 0")
    private Integer  publicationYear;
    
}
