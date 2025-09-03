package com.exam.book.services;

import com.exam.book.constant.Message;
import com.exam.book.constant.StaticVariable;
import com.exam.book.dto.request.BookRequest;
import com.exam.book.dto.response.BookResponse;
import com.exam.book.dto.response.DeleteResponse;
import com.exam.book.entities.BookEntity;
import com.exam.book.enums.BookStatus;
import com.exam.book.exceptions.NotFoundException;
import com.exam.book.mappers.BookMapper;
import com.exam.book.repositories.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;


    @Test
    void testGetAllBookSuccess() throws Exception {
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setBookId(1);
        book.setBookName("test");
        book.setIsbn("1234");
        book.setAuthor("test test");
        book.setBookStatus(BookStatus.Loan);
        books.add(book);
        book = new BookEntity();
        book.setBookId(2);
        book.setBookName("test2");
        book.setIsbn("1235");
        book.setAuthor("test test test");
        book.setBookStatus(BookStatus.Pending);
        books.add(book);
        Mockito.when(bookRepository.findAll()).thenReturn(books);
        List<BookResponse> bookResponses = bookService.getAllBook();
        Assertions.assertEquals(2, bookResponses.size());
    }

    @Test
    void testGetAllBookNotFound() {
        List<BookEntity> books = new ArrayList<>();
        Mockito.when(bookRepository.findAll()).thenReturn(books);
        Assertions.assertThrows(NotFoundException.class, () -> bookService.getAllBook());
    }

    @Test
    void testGetBookSuccess() throws Exception {
        BookEntity book = getBookInput();
        BookResponse dto = getBookOutput(book);
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toResponse(book)).thenReturn(dto);
        BookResponse response = bookService.getBook(1);
        Assertions.assertEquals(response.getBookId(), book.getBookId());
    }

    @Test
    void testGetBookNotFound() {
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class,()->bookService.getBook(1));
    }

    @Test
    void testCreateBookSuccess() throws Exception {
        BookRequest bookRequest = getCreateBookInput();
        BookEntity entity = getCreateBookEntityInput(bookRequest);
        BookResponse mockResponse = getCreatedBookResponse(entity);

        Mockito.when(bookMapper.toEntity(bookRequest)).thenReturn(entity);
        Mockito.when(bookRepository.save(entity)).thenReturn(entity);
        Mockito.when(bookMapper.toResponse(entity)).thenReturn(mockResponse);

        BookResponse response =  bookService.createBook(bookRequest);
        Assertions.assertEquals(bookRequest.getBookName(),response.getBookName());
    }

    @Test
    void testUpdateBookSuccess() throws Exception{
        BookRequest bookRequest = getCreateBookInput();
        BookEntity entity = getUpdateBookEntityInput(bookRequest);
        BookResponse mockResponse = getCreatedBookResponse(entity);

        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(entity));
        Mockito.when(bookRepository.save(entity)).thenReturn(entity);
        Mockito.when(bookMapper.toResponse(entity)).thenReturn(mockResponse);

        BookResponse response =  bookService.updateBook(bookRequest,1);
        Assertions.assertEquals(bookRequest.getBookName(),response.getBookName());
    }

    @Test
    void testUpdateBookNotFound() {
        BookRequest bookRequest = getCreateBookInput();
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class,()->bookService.updateBook(bookRequest,1));
    }

    @Test
    void testDeleteBookSuccess() throws Exception {
        BookEntity entity = getBookInput();
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(entity));
        DeleteResponse response = bookService.deleteBook(1);
        Assertions.assertEquals(Message.DELETE_BOOK_SUCCESS,response.getMessage());
    }

    @Test
    void testDeleteBookNotFound() {
        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class,()->bookService.deleteBook(1));
    }

    private static BookResponse getCreatedBookResponse(BookEntity entity) {
        BookResponse mockResponse = new BookResponse();
        mockResponse.setBookId(entity.getBookId());
        mockResponse.setBookName(entity.getBookName());
        mockResponse.setIsbn(entity.getIsbn());
        mockResponse.setAuthor(entity.getAuthor());
        mockResponse.setBookStatus(entity.getBookStatus());
        return mockResponse;
    }

    private static BookEntity getUpdateBookEntityInput(BookRequest bookRequest) {
        BookEntity entity = new BookEntity();
        entity.setBookId(1);
        entity.setBookName(bookRequest.getBookName());
        entity.setIsbn(bookRequest.getIsbn());
        entity.setAuthor(bookRequest.getAuthor());
        entity.setBookStatus(BookStatus.valueOf(bookRequest.getBookStatus()));
        entity.setCreatedBy(StaticVariable.CREATED_BY);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setUpdatedBy(StaticVariable.UPDATED_BY);
        entity.setUpdatedDate(LocalDateTime.now());
        return entity;
    }

    private static BookEntity getCreateBookEntityInput(BookRequest bookRequest) {
        BookEntity entity = new BookEntity();
        entity.setBookId(1);
        entity.setBookName(bookRequest.getBookName());
        entity.setIsbn(bookRequest.getIsbn());
        entity.setAuthor(bookRequest.getAuthor());
        entity.setBookStatus(BookStatus.valueOf(bookRequest.getBookStatus()));
        entity.setCreatedBy(StaticVariable.CREATED_BY);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setUpdatedBy(entity.getCreatedBy());
        entity.setUpdatedDate(entity.getCreatedDate());
        return entity;
    }

    private static BookRequest getCreateBookInput() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setBookName("test test");
        bookRequest.setIsbn("1234");
        bookRequest.setAuthor("test");
        bookRequest.setBookStatus(BookStatus.Pending.toString());
        return bookRequest;
    }

    private static BookResponse getBookOutput(BookEntity book) {
        BookResponse dto = getCreatedBookResponse(book);
        return dto;
    }

    private static BookEntity getBookInput() {
        BookEntity book = new BookEntity();
        book.setBookId(1);
        book.setBookName("test test");
        book.setIsbn("1234");
        book.setAuthor("test");
        book.setBookStatus(BookStatus.Pending);
        book.setCreatedBy(StaticVariable.CREATED_BY);
        book.setCreatedDate(LocalDateTime.now());
        book.setUpdatedBy(book.getCreatedBy());
        book.setUpdatedDate(book.getCreatedDate());
        return book;
    }

}
