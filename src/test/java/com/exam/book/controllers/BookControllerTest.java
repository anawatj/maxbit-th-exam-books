package com.exam.book.controllers;

import com.exam.book.constant.Message;
import com.exam.book.dto.request.BookRequest;
import com.exam.book.dto.response.BookResponse;
import com.exam.book.dto.response.DeleteResponse;
import com.exam.book.enums.BookStatus;
import com.exam.book.exceptions.NotFoundException;
import com.exam.book.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // mock Service และ inject เข้า Controller
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllBookSuccess() throws Exception {
        List<BookResponse> books = new ArrayList<>();
        BookResponse book = new BookResponse();
        book.setBookId(1);
        book.setBookName("test");
        book.setIsbn("1234");
        book.setAuthor("test");
        book.setBookStatus(BookStatus.Pending);
        books.add(book);
        book = new BookResponse();
        book.setBookId(2);
        book.setBookName("test2");
        book.setIsbn("1235");
        book.setAuthor("test2");
        book.setBookStatus(BookStatus.Loan);
        books.add(book);
        Mockito.when(bookService.getAllBook()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk());

    }

    @Test
    void testGetAllBookNotFound() throws Exception {
        Mockito.when(bookService.getAllBook()).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/books"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllBookInternalServerError() throws Exception {
        Mockito.when(bookService.getAllBook()).thenThrow(Exception.class);
        mockMvc.perform(get("/books"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetBookSuccess() throws Exception {
        BookResponse book = new BookResponse();
        book.setBookId(1);
        book.setBookName("test");
        book.setIsbn("1234");
        book.setAuthor("test");
        book.setBookStatus(BookStatus.Pending);
        when(bookService.getBook(1)).thenReturn(book);
        mockMvc.perform(get("/books/{id}",1))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBookNotFound() throws Exception {

        when(bookService.getBook(1)).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/books/{id}",1))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetBookInternalServerError() throws Exception {

        when(bookService.getBook(1)).thenThrow(Exception.class);
        mockMvc.perform(get("/books/{id}",1))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testCreateBookSuccess() throws Exception {

        BookRequest request = new BookRequest();
        request.setBookName("test");
        request.setIsbn("1234");
        request.setAuthor("test");
        request.setBookStatus(BookStatus.Pending.toString());
        BookResponse response = new BookResponse();
        response.setBookId(1);
        response.setBookName("test");
        response.setIsbn("1234");
        response.setAuthor("test");
        response.setBookStatus(BookStatus.Pending);

        Mockito.when(bookService.createBook(request)).thenReturn(response);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

    }

    @Test
    void testCreateBookBadRequest() throws Exception {

        BookRequest request = new BookRequest();
        request.setBookName("");
        request.setIsbn("");
        request.setAuthor("");
        request.setBookStatus(null);
        BookResponse response = new BookResponse();
        response.setBookId(1);
        response.setBookName("test");
        response.setIsbn("1234");
        response.setAuthor("test");
        response.setBookStatus(BookStatus.Pending);

        //Mockito.when(bookService.createBook(request)).thenThrow(new ValidationException(""));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testCreateBookInternalServerError() throws Exception {

        BookRequest request = new BookRequest();
        request.setBookName("11");
        request.setIsbn("11");
        request.setAuthor("11");
        request.setBookStatus(BookStatus.Pending.toString());
        BookResponse response = new BookResponse();
        response.setBookId(1);
        response.setBookName("test");
        response.setIsbn("1234");
        response.setAuthor("test");
        response.setBookStatus(BookStatus.Pending);

        Mockito.when(bookService.createBook(request)).thenThrow(new Exception(""));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    void testUpdateBookSuccess() throws Exception {

        BookRequest request = new BookRequest();
        request.setBookName("test");
        request.setIsbn("1234");
        request.setAuthor("test");
        request.setBookStatus(BookStatus.Pending.toString());
        BookResponse response = new BookResponse();
        response.setBookId(1);
        response.setBookName("test");
        response.setIsbn("1234");
        response.setAuthor("test");
        response.setBookStatus(BookStatus.Pending);

        Mockito.when(bookService.updateBook(request,1)).thenReturn(response);

        mockMvc.perform(put("/books/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @Test
    void testUpdateBookBadRequest() throws Exception {

        BookRequest request = new BookRequest();
        request.setBookName("");
        request.setIsbn("");
        request.setAuthor("");
        request.setBookStatus(BookStatus.Pending.toString());
        BookResponse response = new BookResponse();
        response.setBookId(1);
        response.setBookName("test");
        response.setIsbn("1234");
        response.setAuthor("test");
        response.setBookStatus(BookStatus.Pending);

        //Mockito.when(bookService.updateBook(request,1)).thenThrow(new ValidationException(""));

        mockMvc.perform(put("/books/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testUpdateBookNotFound() throws Exception {

        BookRequest request = new BookRequest();
        request.setBookName("test");
        request.setIsbn("1234");
        request.setAuthor("test");
        request.setBookStatus(BookStatus.Pending.toString());

        Mockito.when(bookService.updateBook(request,1)).thenThrow(new NotFoundException(""));

        mockMvc.perform(put("/books/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

    }

    @Test
    void testUpdateBookInternalServerError() throws Exception {

        BookRequest request = new BookRequest();
        request.setBookName("test");
        request.setIsbn("1234");
        request.setAuthor("test");
        request.setBookStatus(BookStatus.Pending.toString());
        BookResponse response = new BookResponse();
        response.setBookId(1);
        response.setBookName("test");
        response.setIsbn("1234");
        response.setAuthor("test");
        response.setBookStatus(BookStatus.Pending);

        Mockito.when(bookService.updateBook(request,1)).thenThrow(new Exception());

        mockMvc.perform(put("/books/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    void testDeleteBookSuccess() throws  Exception{
        DeleteResponse response = new DeleteResponse();
        response.setMessage(Message.DELETE_BOOK_SUCCESS);
        Mockito.when(bookService.deleteBook(1)).thenReturn(response);

        mockMvc.perform(delete("/books/{id}",1))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteBookNotFound() throws  Exception{
        DeleteResponse response = new DeleteResponse();
        response.setMessage(Message.DELETE_BOOK_SUCCESS);
        Mockito.when(bookService.deleteBook(1)).thenThrow(new NotFoundException(Message.BOOK_NOT_FOUND));

        mockMvc.perform(delete("/books/{id}",1))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteBookInternalServerError() throws  Exception{
        DeleteResponse response = new DeleteResponse();
        response.setMessage(Message.DELETE_BOOK_SUCCESS);
        Mockito.when(bookService.deleteBook(1)).thenThrow(new Exception(""));

        mockMvc.perform(delete("/books/{id}",1))
                .andExpect(status().isInternalServerError());
    }
}
