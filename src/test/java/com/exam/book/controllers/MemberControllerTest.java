package com.exam.book.controllers;

import com.exam.book.dto.request.MemberRequest;
import com.exam.book.dto.response.MemberResponse;
import com.exam.book.enums.Role;
import com.exam.book.exceptions.BadRequestException;
import com.exam.book.services.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCreateMemberSuccess() throws Exception {
        MemberRequest request = new MemberRequest();
        request.setMemberName("test");
        request.setMemberLastname("test test");
        request.setEmail("test@gmail.com");
        request.setPassword("P@ssw0rd");
        request.setRole("User");

        MemberResponse response = new MemberResponse();
        response.setMemberId(1);
        response.setMemberName("test");
        response.setMemberLastname("test test");
        request.setEmail("test@gmail.com");
        response.setPassword("P@ssw0rd");
        response.setRole("User");

        Mockito.when(memberService.createMember(request)).thenReturn(response);

         mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateMemberInvalidInput() throws Exception {
        MemberRequest request = new MemberRequest();
        request.setMemberName("");
        request.setMemberLastname("");
        request.setEmail("");
        request.setPassword("");
        request.setRole(null);



        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateMemberExistsEmail() throws Exception {
        MemberRequest request = new MemberRequest();
        request.setMemberName("test");
        request.setMemberLastname("test test");
        request.setEmail("test@gmail.com");
        request.setPassword("P@ssw0rd");
        request.setRole("User");

        when(memberService.createMember(request)).thenThrow(BadRequestException.class);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateMemberInternalServerError() throws Exception {
        MemberRequest request = new MemberRequest();
        request.setMemberName("test");
        request.setMemberLastname("test test");
        request.setEmail("test@gmail.com");
        request.setPassword("P@ssw0rd");
        request.setRole("User");

        when(memberService.createMember(request)).thenThrow(Exception.class);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}
