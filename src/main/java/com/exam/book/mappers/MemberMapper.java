package com.exam.book.mappers;

import com.exam.book.dto.request.MemberRequest;
import com.exam.book.dto.response.MemberResponse;
import com.exam.book.entities.MemberEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberEntity toEntity(MemberRequest request);
    MemberResponse toResponse(MemberEntity entity);
}
