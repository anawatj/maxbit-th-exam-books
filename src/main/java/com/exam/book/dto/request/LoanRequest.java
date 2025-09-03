package com.exam.book.dto.request;

import com.exam.book.constant.ValidationError;
import com.exam.book.enums.LoanStatus;
import com.exam.book.validators.ValidLoanStatus;
import com.exam.book.validators.ValidLocalDate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class LoanRequest {
    @ValidLocalDate(past = true,present = true,message = ValidationError.LOAN_DATE_NOT_EMPTY)
    private LocalDate loanDate;
    @ValidLocalDate(past = true,present = true,message = ValidationError.RETURN_DATE_NOT_EMPTY)
    private LocalDate returnDate;
    @ValidLoanStatus(enumClass = LoanStatus.class,message = ValidationError.LOAN_STATUS_IS_NOT_EMPTY)
    private String loanStatus;
    @NotNull(message = ValidationError.MEMBER_ID_IS_NOT_NULL)
    private Integer memberId;
    @NotNull(message = ValidationError.BOOKS_IS_NOT_NULL)
    private List<LoanBookRequest> books;
}
