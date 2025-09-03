package com.exam.book.dto.response;

import lombok.Data;

@Data
public class LoanBookResponse {
    private Integer bookId;
    private String bookName;
    private String isbn;
    private String author;
}
