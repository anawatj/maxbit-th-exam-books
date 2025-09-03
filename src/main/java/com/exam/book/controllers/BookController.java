package com.exam.book.controllers;

import com.exam.book.dto.request.BookRequest;
import com.exam.book.exceptions.NotFoundException;
import com.exam.book.services.BookService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<?> getAllBook(){
        try{
            return ResponseEntity.ok(bookService.getAllBook());
        }catch(Exception e){
            if(e instanceof  NotFoundException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
    @GetMapping(value = "/{bookId}")
    public ResponseEntity<?> getBook(@PathVariable Integer bookId){
        try{
            return ResponseEntity.ok(bookService.getBook(bookId));
        }catch (Exception e){
            if(e instanceof NotFoundException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookRequest bookRequest){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookRequest));
        }catch (Exception e){
            if(e instanceof ValidationException){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }

        }
    }
    @PutMapping(value = "/{bookId}")
    public ResponseEntity<?> updateBook(@RequestBody BookRequest bookRequest,@PathVariable Integer bookId){
        try{
            return ResponseEntity.ok(bookService.updateBook(bookRequest,bookId));
        }catch(Exception e){
            if(e instanceof NotFoundException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }else if(e instanceof  ValidationException){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
    @DeleteMapping(value = "/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer bookId){
        try{
            return ResponseEntity.ok(bookService.deleteBook(bookId));
        }catch(Exception e){
            if(e instanceof NotFoundException){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
    }
}
