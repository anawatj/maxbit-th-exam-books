package com.exam.book.mappers;

import com.exam.book.dto.request.BookRequest;
import com.exam.book.dto.response.BookResponse;
import com.exam.book.entities.BookEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookEntity toEntity(BookRequest request);
    BookResponse toResponse(BookEntity entity);

}
