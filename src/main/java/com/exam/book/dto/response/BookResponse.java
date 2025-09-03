package com.exam.book.dto.response;

import com.exam.book.enums.BookStatus;
import lombok.Data;

@Data
public class BookResponse {
    private Integer bookId;
    private String  bookName;
    private String  isbn;
    private String  author;
    private BookStatus bookStatus;
}
