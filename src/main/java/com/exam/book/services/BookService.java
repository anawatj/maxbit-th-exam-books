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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper mapper;


    public List<BookResponse> getAllBook() throws Exception {
        List<BookEntity> bookEntities = bookRepository.findAll();
        if(bookEntities.isEmpty()){
            throw new NotFoundException(Message.BOOK_NOT_FOUND);
        }
        return bookEntities.stream().map(mapper::toResponse).toList();

    }
    public BookResponse getBook(Integer bookId) throws Exception{
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(()->new NotFoundException(Message.BOOK_NOT_FOUND));
        return mapper.toResponse(bookEntity);
    }
    @Transactional(rollbackFor = Exception.class)
    public BookResponse createBook(BookRequest bookRequest) throws Exception{
        BookEntity bookEntity = getCreateBookEntity(bookRequest);
        final BookEntity savedBook = bookRepository.save(bookEntity);
        return mapper.toResponse(savedBook);
    }

    @Transactional(rollbackFor = Exception.class)
    public BookResponse updateBook(BookRequest bookRequest,Integer bookId) throws Exception{
        BookEntity bookEntity = getUpdateBookEntity(bookRequest, bookId);
        final BookEntity savedBook = bookRepository.save(bookEntity);
        return mapper.toResponse(savedBook);
    }

    @Transactional(rollbackFor = Exception.class)
    public DeleteResponse deleteBook(Integer bookId) throws Exception{
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(()->new NotFoundException(Message.BOOK_NOT_FOUND));
        bookRepository.delete(bookEntity);
        DeleteResponse response = new DeleteResponse();
        response.setMessage(Message.DELETE_BOOK_SUCCESS);
        return response;
    }

    private BookEntity getUpdateBookEntity(BookRequest bookRequest, Integer bookId) throws NotFoundException {
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(()->new NotFoundException(Message.BOOK_NOT_FOUND));
        bookEntity.setBookName(bookRequest.getBookName());
        bookEntity.setIsbn(bookRequest.getIsbn());
        bookEntity.setAuthor(bookRequest.getAuthor());
        bookEntity.setBookStatus(BookStatus.valueOf(bookRequest.getBookStatus()));
        bookEntity.setUpdatedBy(StaticVariable.UPDATED_BY);
        bookEntity.setUpdatedDate(LocalDateTime.now());
        return bookEntity;
    }

    private BookEntity getCreateBookEntity(BookRequest bookRequest) {
        BookEntity bookEntity = mapper.toEntity(bookRequest);
        bookEntity.setCreatedBy(StaticVariable.CREATED_BY);
        bookEntity.setCreatedDate(LocalDateTime.now());
        bookEntity.setUpdatedBy(bookEntity.getCreatedBy());
        bookEntity.setUpdatedDate(bookEntity.getCreatedDate());
        return bookEntity;
    }

}
