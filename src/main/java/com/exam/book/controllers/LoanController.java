package com.exam.book.controllers;

import com.exam.book.dto.request.LoanRequest;
import com.exam.book.exceptions.NotFoundException;
import com.exam.book.services.LoanService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<?> getAllLoan(){
        try{
            return ResponseEntity.ok(loanService.getAllLoan());
        }catch (Exception e){
            if(e instanceof NotFoundException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
    @GetMapping(value = "/{loanId}")
    public ResponseEntity<?> getLoan(@PathVariable Integer loanId){
        try{
            return ResponseEntity.ok(loanService.getLoan(loanId));
        }catch (Exception e){
            if(e instanceof NotFoundException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
    @PostMapping
    public ResponseEntity<?> createLoan(@Valid  @RequestBody LoanRequest loanRequest){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(loanService.createLoan(loanRequest));
        }catch (Exception e){
            if(e instanceof NotFoundException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
    @PutMapping(value = "/{loanId}")
    public ResponseEntity<?> updateLoan(@Valid @RequestBody LoanRequest loanRequest,@PathVariable Integer loanId){
        try{
            return ResponseEntity.ok(loanService.updateLoan(loanRequest,loanId));
        }catch (Exception e){
            if(e instanceof NotFoundException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
    @DeleteMapping(value = "/{loanId}")
    public ResponseEntity<?> deleteLoan(@PathVariable Integer loanId){
        try{
            return ResponseEntity.ok(loanService.deleteLoan(loanId));
        }catch (Exception e){
            if(e instanceof NotFoundException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
}
