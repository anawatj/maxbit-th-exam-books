package com.exam.book.dto.request;

import com.exam.book.constant.ValidationError;
import com.exam.book.enums.BookStatus;
import com.exam.book.validators.ValidBookStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRequest {
    @NotBlank(message = ValidationError.BOOK_NAME_NOT_EMPTY)
    private String bookName;
    @NotBlank(message = ValidationError.ISBN_NOT_EMPTY)
    private String isbn;
    @NotBlank(message = ValidationError.AUTHOR_NOT_EMPTY)
    private String author;
    @ValidBookStatus(enumClass = BookStatus.class, message = ValidationError.BOOK_STATUS_IS_NOT_EMPTY)
    private String bookStatus;
}
