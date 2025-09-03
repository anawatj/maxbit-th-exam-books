package com.exam.book.dto.response;

import com.exam.book.enums.LoanStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class LoanResponse {
    private Integer loanId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LoanStatus loanStatus;
    private Integer memberId;
    private List<LoanBookResponse> books;
}
