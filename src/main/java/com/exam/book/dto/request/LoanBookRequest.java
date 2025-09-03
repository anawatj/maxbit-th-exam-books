package com.exam.book.dto.request;

import com.exam.book.constant.ValidationError;
import com.exam.book.enums.BookStatus;
import com.exam.book.validators.ValidBookStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LoanBookRequest {
    @Positive(message = ValidationError.BOOK_ID_IS_POSITIVE)
    private Integer bookId;
}
