package com.exam.book.dto.request;

import com.exam.book.constant.ValidationError;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LoanBookRequest {
    @Positive(message = ValidationError.BOOK_ID_IS_POSITIVE)
    private Integer bookId;
}
