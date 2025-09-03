package com.exam.book.mappers;

import com.exam.book.dto.request.LoanBookRequest;
import com.exam.book.dto.request.LoanRequest;
import com.exam.book.dto.response.BookResponse;
import com.exam.book.dto.response.LoanBookResponse;
import com.exam.book.dto.response.LoanResponse;
import com.exam.book.entities.BookEntity;
import com.exam.book.entities.LoanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanEntity toEntity(LoanRequest request);
    BookEntity toBookEntity(LoanBookRequest request);


    LoanResponse toResponse(LoanEntity entity);
    LoanBookResponse toBookResponse(BookEntity entity);
}
