package com.exam.book.services;

import com.exam.book.constant.Message;
import com.exam.book.dto.request.MemberRequest;
import com.exam.book.dto.response.MemberResponse;
import com.exam.book.entities.MemberEntity;
import com.exam.book.exceptions.BadRequestException;
import com.exam.book.mappers.MemberMapper;
import com.exam.book.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper mapper;

    @Transactional
    public MemberResponse createMember(MemberRequest memberRequest) throws Exception {
        Optional<MemberEntity> existsMember =  memberRepository.findByEmail(memberRequest.getEmail());
        if(existsMember.isPresent()){
            throw new BadRequestException(Message.MEMBER_IS_EXISTS);
        }
        MemberEntity entity = mapper.toEntity(memberRequest);
        final MemberEntity savedMember = memberRepository.save(entity);
        return mapper.toResponse(savedMember);
    }
}
