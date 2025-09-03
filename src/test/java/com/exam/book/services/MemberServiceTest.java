package com.exam.book.services;

import com.exam.book.dto.request.MemberRequest;
import com.exam.book.dto.response.MemberResponse;
import com.exam.book.entities.MemberEntity;
import com.exam.book.enums.Role;
import com.exam.book.exceptions.BadRequestException;
import com.exam.book.mappers.MemberMapper;
import com.exam.book.repositories.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberMapper mapper;

    @InjectMocks
    private MemberService memberService;

    @Test
    void createMemberWithAdmin() throws Exception {
        MemberRequest request = new MemberRequest();
        request.setMemberName("admin");
        request.setMemberLastname("admin admin");
        request.setEmail("ajarusiripot3@gmail.com");
        request.setPassword("P@ssw0rd");
        request.setRole(Role.Admin.toString());

        MemberEntity entity = new MemberEntity();
        entity.setMemberId(1);
        entity.setMemberName(request.getMemberName());
        entity.setMemberLastname(request.getMemberLastname());
        entity.setEmail(request.getEmail());
        entity.setPassword(request.getPassword());
        entity.setRole(Role.valueOf(request.getRole()));

        MemberResponse response = new MemberResponse();
        response.setMemberId(1);
        response.setMemberName(entity.getMemberName());
        response.setMemberLastname(entity.getMemberLastname());
        response.setEmail(entity.getEmail());
        response.setPassword(entity.getPassword());
        response.setRole(entity.getRole().toString());

        Mockito.when(mapper.toEntity(request)).thenReturn(entity);
        Mockito.when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        Mockito.when(memberRepository.save(entity)).thenReturn(entity);
        Mockito.when(mapper.toResponse(entity)).thenReturn(response);

        MemberResponse result = memberService.createMember(request);

        Assertions.assertEquals("Admin",result.getRole());
    }

    @Test
    void createMemberWithUser() throws Exception {
        MemberRequest request = new MemberRequest();
        request.setMemberName("user");
        request.setMemberLastname("user user");
        request.setEmail("ajarusiripot3@gmail.com");
        request.setPassword("P@ssw0rd");
        request.setRole(Role.User.toString());

        MemberEntity entity = new MemberEntity();
        entity.setMemberId(1);
        entity.setMemberName(request.getMemberName());
        entity.setMemberLastname(request.getMemberLastname());
        entity.setEmail(request.getEmail());
        entity.setPassword(request.getPassword());
        entity.setRole(Role.valueOf(request.getRole()));

        MemberResponse response = new MemberResponse();
        response.setMemberId(1);
        response.setMemberName(entity.getMemberName());
        response.setMemberLastname(entity.getMemberLastname());
        response.setEmail(entity.getEmail());
        response.setPassword(entity.getPassword());
        response.setRole(entity.getRole().toString());

        Mockito.when(mapper.toEntity(request)).thenReturn(entity);
        Mockito.when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        Mockito.when(memberRepository.save(entity)).thenReturn(entity);
        Mockito.when(mapper.toResponse(entity)).thenReturn(response);

        MemberResponse result = memberService.createMember(request);

        Assertions.assertEquals("User",result.getRole());
    }

    @Test
    void createMemberWithExistEmail()  {
        MemberRequest request = new MemberRequest();
        request.setMemberName("user");
        request.setMemberLastname("user user");
        request.setEmail("ajarusiripot3@gmail.com");
        request.setPassword("P@ssw0rd");
        request.setRole(Role.User.toString());

        MemberEntity entity = new MemberEntity();
        entity.setMemberId(1);
        entity.setMemberName(request.getMemberName());
        entity.setMemberLastname(request.getMemberLastname());
        entity.setEmail(request.getEmail());
        entity.setPassword(request.getPassword());
        entity.setRole(Role.valueOf(request.getRole()));

        MemberResponse response = new MemberResponse();
        response.setMemberId(1);
        response.setMemberName(entity.getMemberName());
        response.setMemberLastname(entity.getMemberLastname());
        response.setEmail(entity.getEmail());
        response.setPassword(entity.getPassword());
        response.setRole(entity.getRole().toString());

        Mockito.when(memberRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(entity));


        Assertions.assertThrows(BadRequestException.class,()->memberService.createMember(request));
    }
}
