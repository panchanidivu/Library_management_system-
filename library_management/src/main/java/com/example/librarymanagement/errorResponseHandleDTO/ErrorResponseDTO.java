package com.example.librarymanagement.errorResponseHandleDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    
    private String errorCode;
    private String errorMessage;

}
