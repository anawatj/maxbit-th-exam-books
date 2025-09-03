package com.exam.book.controllers;

import com.exam.book.dto.request.MemberRequest;
import com.exam.book.exceptions.BadRequestException;
import com.exam.book.services.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public ResponseEntity<?> createMember(@Valid @RequestBody MemberRequest request){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(request));
        }catch(Exception e){
            if(e instanceof BadRequestException){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
}
