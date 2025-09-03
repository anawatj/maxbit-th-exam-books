package com.exam.book.services;

import com.exam.book.constant.Message;
import com.exam.book.constant.StaticVariable;
import com.exam.book.dto.request.LoanBookRequest;
import com.exam.book.dto.request.LoanRequest;
import com.exam.book.dto.response.DeleteResponse;
import com.exam.book.dto.response.LoanBookResponse;
import com.exam.book.dto.response.LoanResponse;
import com.exam.book.entities.BookEntity;
import com.exam.book.entities.LoanEntity;
import com.exam.book.entities.MemberEntity;
import com.exam.book.enums.BookStatus;
import com.exam.book.enums.LoanStatus;
import com.exam.book.exceptions.NotFoundException;
import com.exam.book.mappers.LoanMapper;
import com.exam.book.repositories.BookRepository;
import com.exam.book.repositories.LoanRepository;
import com.exam.book.repositories.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {
    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private LoanService loanService;

    @Test
    void testGetAllLoanSuccess() throws Exception {
        List<LoanEntity> mockList = new ArrayList<>();
        LoanEntity mockItem = new LoanEntity();
        mockItem.setLoanId(1);
        MemberEntity mockMember = new MemberEntity();
        mockMember.setMemberId(1);
        mockItem.setMember(mockMember);
        mockItem.setLoanDate(LocalDate.now());
        mockItem.setReturnDate(LocalDate.now());
        mockItem.setLoanStatus(LoanStatus.Loan);
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setBookId(1);
        books.add(book);
        mockItem.setBooks(books);
        mockList.add(mockItem);

        LoanResponse mockResponse = new LoanResponse();
        mockResponse.setLoanId(mockItem.getLoanId());
        mockResponse.setMemberId(mockItem.getMember().getMemberId());
        mockResponse.setLoanDate(mockItem.getLoanDate());
        mockResponse.setLoanStatus(mockItem.getLoanStatus());
        mockResponse.setReturnDate(mockItem.getReturnDate());
        List<LoanBookResponse> mockBookResponses = new ArrayList<>();
        LoanBookResponse mockBookResponse = new LoanBookResponse();
        mockBookResponse.setBookId(book.getBookId());
        mockBookResponses.add(mockBookResponse);
        mockResponse.setBooks(mockBookResponses);


        Mockito.when(loanRepository.findAll()).thenReturn(mockList);
        Mockito.when(loanMapper.toResponse(mockItem)).thenReturn(mockResponse);
        Mockito.when(loanMapper.toBookResponse(book)).thenReturn(mockBookResponse);

        List<LoanResponse> responses = loanService.getAllLoan();

        Assertions.assertEquals(1, responses.size());
    }

    @Test
    void testGetAllLoanNotFound() {
        List<LoanEntity> mockList = new ArrayList<>();
        Mockito.when(loanRepository.findAll()).thenReturn(mockList);
        Assertions.assertThrows(NotFoundException.class, () -> loanService.getAllLoan());
    }

    @Test
    void testGetLoan() throws Exception {
        LoanEntity mockItem = new LoanEntity();
        mockItem.setLoanId(1);
        MemberEntity mockMember = new MemberEntity();
        mockMember.setMemberId(1);
        mockItem.setMember(mockMember);
        mockItem.setLoanDate(LocalDate.now());
        mockItem.setReturnDate(LocalDate.now());
        mockItem.setLoanStatus(LoanStatus.Loan);
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setBookId(1);
        books.add(book);
        mockItem.setBooks(books);

        LoanResponse mockResponse = new LoanResponse();
        mockResponse.setLoanId(mockItem.getLoanId());
        mockResponse.setMemberId(mockItem.getMember().getMemberId());
        mockResponse.setLoanDate(mockItem.getLoanDate());
        mockResponse.setLoanStatus(mockItem.getLoanStatus());
        mockResponse.setReturnDate(mockItem.getReturnDate());
        List<LoanBookResponse> mockBookResponses = new ArrayList<>();
        LoanBookResponse mockBookResponse = new LoanBookResponse();
        mockBookResponse.setBookId(book.getBookId());
        mockBookResponses.add(mockBookResponse);
        mockResponse.setBooks(mockBookResponses);

        Mockito.when(loanRepository.findById(1)).thenReturn(Optional.of(mockItem));
        Mockito.when(loanMapper.toResponse(mockItem)).thenReturn(mockResponse);
        Mockito.when(loanMapper.toBookResponse(book)).thenReturn(mockBookResponse);

        LoanResponse response = loanService.getLoan(1);
        Assertions.assertEquals(1, response.getLoanId());
    }

    @Test
    void testGetLoanNotFound() {
        Mockito.when(loanRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> loanService.getLoan(1));
    }

    @Test
    void testCreateLoanWithPendingSuccess() throws Exception {
        LoanRequest request = new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(LocalDate.now());
        request.setLoanStatus(LoanStatus.Pending.toString());
        request.setReturnDate(LocalDate.now());
        List<LoanBookRequest> mockBookRequests = new ArrayList<>();
        LoanBookRequest mockBookRequest = new LoanBookRequest();
        mockBookRequest.setBookId(1);
        mockBookRequests.add(mockBookRequest);
        request.setBooks(mockBookRequests);

        LoanEntity mockItem = new LoanEntity();
        mockItem.setLoanId(1);
        MemberEntity mockMember = new MemberEntity();
        mockMember.setMemberId(request.getMemberId());
        mockItem.setMember(mockMember);
        mockItem.setLoanDate(request.getLoanDate());
        mockItem.setReturnDate(request.getReturnDate());
        mockItem.setLoanStatus(LoanStatus.Pending);
        mockItem.setCreatedBy(StaticVariable.CREATED_BY);
        mockItem.setCreatedDate(LocalDateTime.now());
        mockItem.setUpdatedBy(mockItem.getCreatedBy());
        mockItem.setUpdatedDate(mockItem.getCreatedDate());
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setBookId(1);
        books.add(book);
        mockItem.setBooks(books);

        LoanResponse mockResponse = new LoanResponse();
        mockResponse.setLoanId(mockItem.getLoanId());
        mockResponse.setMemberId(mockItem.getMember().getMemberId());
        mockResponse.setLoanDate(mockItem.getLoanDate());
        mockResponse.setLoanStatus(mockItem.getLoanStatus());
        mockResponse.setReturnDate(mockItem.getReturnDate());
        List<LoanBookResponse> mockBookResponses = new ArrayList<>();
        LoanBookResponse mockBookResponse = new LoanBookResponse();
        mockBookResponse.setBookId(book.getBookId());
        mockBookResponses.add(mockBookResponse);
        mockResponse.setBooks(mockBookResponses);

        Mockito.when(loanMapper.toEntity(request)).thenReturn(mockItem);
        Mockito.when(loanMapper.toBookEntity(mockBookRequest)).thenReturn(book);
        Mockito.when(memberRepository.findById(1)).thenReturn(Optional.of(mockMember));
        Mockito.when(loanRepository.save(mockItem)).thenReturn(mockItem);
        Mockito.when(loanMapper.toResponse(mockItem)).thenReturn(mockResponse);
        Mockito.when(loanMapper.toBookResponse(book)).thenReturn(mockBookResponse);


        LoanResponse response = loanService.createLoan(request);

        Assertions.assertEquals(1, response.getLoanId());

    }

    @Test
    void testCreateLoanWithLoanSuccess() throws Exception {
        LoanRequest request = new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(LocalDate.now());
        request.setLoanStatus(LoanStatus.Loan.toString());
        request.setReturnDate(LocalDate.now());
        List<LoanBookRequest> mockBookRequests = new ArrayList<>();
        LoanBookRequest mockBookRequest = new LoanBookRequest();
        mockBookRequest.setBookId(1);
        mockBookRequests.add(mockBookRequest);
        request.setBooks(mockBookRequests);

        LoanEntity mockItem = new LoanEntity();
        mockItem.setLoanId(1);
        MemberEntity mockMember = new MemberEntity();
        mockMember.setMemberId(request.getMemberId());
        mockItem.setMember(mockMember);
        mockItem.setLoanDate(request.getLoanDate());
        mockItem.setReturnDate(request.getReturnDate());
        mockItem.setLoanStatus(LoanStatus.Loan);
        mockItem.setCreatedBy(StaticVariable.CREATED_BY);
        mockItem.setCreatedDate(LocalDateTime.now());
        mockItem.setUpdatedBy(mockItem.getCreatedBy());
        mockItem.setUpdatedDate(mockItem.getCreatedDate());
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setBookId(1);
        books.add(book);
        mockItem.setBooks(books);

        LoanResponse mockResponse = new LoanResponse();
        mockResponse.setLoanId(mockItem.getLoanId());
        mockResponse.setMemberId(mockItem.getMember().getMemberId());
        mockResponse.setLoanDate(mockItem.getLoanDate());
        mockResponse.setLoanStatus(mockItem.getLoanStatus());
        mockResponse.setReturnDate(mockItem.getReturnDate());
        List<LoanBookResponse> mockBookResponses = new ArrayList<>();
        LoanBookResponse mockBookResponse = new LoanBookResponse();
        mockBookResponse.setBookId(book.getBookId());
        mockBookResponses.add(mockBookResponse);
        mockResponse.setBooks(mockBookResponses);

        Mockito.when(loanMapper.toEntity(request)).thenReturn(mockItem);
        Mockito.when(loanMapper.toBookEntity(mockBookRequest)).thenReturn(book);
        Mockito.when(memberRepository.findById(1)).thenReturn(Optional.of(mockMember));
        Mockito.when(loanRepository.save(mockItem)).thenReturn(mockItem);
        Mockito.when(loanMapper.toResponse(mockItem)).thenReturn(mockResponse);
        Mockito.when(loanMapper.toBookResponse(book)).thenReturn(mockBookResponse);


        LoanResponse response = loanService.createLoan(request);

        Assertions.assertEquals(1, response.getLoanId());

    }


    @Test
    void testCreateLoanWithMemberEmpty() {
        LoanRequest request = new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(LocalDate.now());
        request.setLoanStatus(LoanStatus.Loan.toString());
        request.setReturnDate(LocalDate.now());
        List<LoanBookRequest> mockBookRequests = new ArrayList<>();
        LoanBookRequest mockBookRequest = new LoanBookRequest();
        mockBookRequest.setBookId(1);
        mockBookRequests.add(mockBookRequest);
        request.setBooks(mockBookRequests);

        LoanEntity mockItem = new LoanEntity();
        mockItem.setLoanId(1);
        MemberEntity mockMember = new MemberEntity();
        mockMember.setMemberId(request.getMemberId());
        mockItem.setMember(mockMember);
        mockItem.setLoanDate(request.getLoanDate());
        mockItem.setReturnDate(request.getReturnDate());
        mockItem.setLoanStatus(LoanStatus.Loan);
        mockItem.setCreatedBy(StaticVariable.CREATED_BY);
        mockItem.setCreatedDate(LocalDateTime.now());
        mockItem.setUpdatedBy(mockItem.getCreatedBy());
        mockItem.setUpdatedDate(mockItem.getCreatedDate());
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setBookId(1);
        books.add(book);
        mockItem.setBooks(books);

        LoanResponse mockResponse = new LoanResponse();
        mockResponse.setLoanId(mockItem.getLoanId());
        mockResponse.setMemberId(mockItem.getMember().getMemberId());
        mockResponse.setLoanDate(mockItem.getLoanDate());
        mockResponse.setLoanStatus(mockItem.getLoanStatus());
        mockResponse.setReturnDate(mockItem.getReturnDate());
        List<LoanBookResponse> mockBookResponses = new ArrayList<>();
        LoanBookResponse mockBookResponse = new LoanBookResponse();
        mockBookResponse.setBookId(book.getBookId());
        mockBookResponses.add(mockBookResponse);
        mockResponse.setBooks(mockBookResponses);

        Mockito.when(loanMapper.toEntity(request)).thenReturn(mockItem);
        Mockito.when(memberRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> loanService.createLoan(request));

    }


    @Test
    void testUpdateLoanWithLoanSuccess() throws Exception {
        LoanRequest request = new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(LocalDate.now());
        request.setLoanStatus(LoanStatus.Loan.toString());
        request.setReturnDate(LocalDate.now());
        List<LoanBookRequest> mockBookRequests = new ArrayList<>();
        LoanBookRequest mockBookRequest = new LoanBookRequest();
        mockBookRequest.setBookId(1);
        mockBookRequests.add(mockBookRequest);
        request.setBooks(mockBookRequests);

        LoanEntity mockItem = new LoanEntity();
        mockItem.setLoanId(1);
        MemberEntity mockMember = new MemberEntity();
        mockMember.setMemberId(request.getMemberId());
        mockItem.setMember(mockMember);
        mockItem.setLoanDate(request.getLoanDate());
        mockItem.setReturnDate(request.getReturnDate());
        mockItem.setLoanStatus(LoanStatus.Loan);
        mockItem.setCreatedBy(StaticVariable.CREATED_BY);
        mockItem.setCreatedDate(LocalDateTime.now());
        mockItem.setUpdatedBy(mockItem.getCreatedBy());
        mockItem.setUpdatedDate(mockItem.getCreatedDate());
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setBookId(1);
        books.add(book);
        mockItem.setBooks(books);

        LoanResponse mockResponse = new LoanResponse();
        mockResponse.setLoanId(mockItem.getLoanId());
        mockResponse.setMemberId(mockItem.getMember().getMemberId());
        mockResponse.setLoanDate(mockItem.getLoanDate());
        mockResponse.setLoanStatus(mockItem.getLoanStatus());
        mockResponse.setReturnDate(mockItem.getReturnDate());
        List<LoanBookResponse> mockBookResponses = new ArrayList<>();
        LoanBookResponse mockBookResponse = new LoanBookResponse();
        mockBookResponse.setBookId(book.getBookId());
        mockBookResponses.add(mockBookResponse);
        mockResponse.setBooks(mockBookResponses);


        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        Mockito.when(loanRepository.findById(1)).thenReturn(Optional.of(mockItem));
        Mockito.when(memberRepository.findById(1)).thenReturn(Optional.of(mockMember));
        Mockito.when(loanRepository.save(mockItem)).thenReturn(mockItem);
        Mockito.when(loanMapper.toResponse(mockItem)).thenReturn(mockResponse);


        LoanResponse response = loanService.updateLoan(request, 1);

        Assertions.assertEquals(1, response.getLoanId());

    }


    @Test
    void testUpdateLoanWithReturnSuccess() throws Exception {
        LoanRequest request = new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(LocalDate.now());
        request.setLoanStatus(LoanStatus.Return.toString());
        request.setReturnDate(LocalDate.now());
        List<LoanBookRequest> mockBookRequests = new ArrayList<>();
        LoanBookRequest mockBookRequest = new LoanBookRequest();
        mockBookRequest.setBookId(1);
        mockBookRequests.add(mockBookRequest);
        request.setBooks(mockBookRequests);

        LoanEntity mockItem = new LoanEntity();
        mockItem.setLoanId(1);
        MemberEntity mockMember = new MemberEntity();
        mockMember.setMemberId(request.getMemberId());
        mockItem.setMember(mockMember);
        mockItem.setLoanDate(request.getLoanDate());
        mockItem.setReturnDate(request.getReturnDate());
        mockItem.setLoanStatus(LoanStatus.Return);
        mockItem.setCreatedBy(StaticVariable.CREATED_BY);
        mockItem.setCreatedDate(LocalDateTime.now());
        mockItem.setUpdatedBy(mockItem.getCreatedBy());
        mockItem.setUpdatedDate(mockItem.getCreatedDate());
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setBookId(1);
        books.add(book);
        mockItem.setBooks(books);

        LoanResponse mockResponse = new LoanResponse();
        mockResponse.setLoanId(mockItem.getLoanId());
        mockResponse.setMemberId(mockItem.getMember().getMemberId());
        mockResponse.setLoanDate(mockItem.getLoanDate());
        mockResponse.setLoanStatus(mockItem.getLoanStatus());
        mockResponse.setReturnDate(mockItem.getReturnDate());
        List<LoanBookResponse> mockBookResponses = new ArrayList<>();
        LoanBookResponse mockBookResponse = new LoanBookResponse();
        mockBookResponse.setBookId(book.getBookId());
        mockBookResponses.add(mockBookResponse);
        mockResponse.setBooks(mockBookResponses);


        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        Mockito.when(loanRepository.findById(1)).thenReturn(Optional.of(mockItem));
        Mockito.when(memberRepository.findById(1)).thenReturn(Optional.of(mockMember));
        Mockito.when(loanRepository.save(mockItem)).thenReturn(mockItem);
        Mockito.when(loanMapper.toResponse(mockItem)).thenReturn(mockResponse);


        LoanResponse response = loanService.updateLoan(request, 1);

        Assertions.assertEquals(1, response.getLoanId());

    }

    @Test
    void testUpdateLoanWithNotFoundLoan() {
        LoanRequest request = new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(LocalDate.now());
        request.setLoanStatus(LoanStatus.Return.toString());
        request.setReturnDate(LocalDate.now());
        List<LoanBookRequest> mockBookRequests = new ArrayList<>();
        LoanBookRequest mockBookRequest = new LoanBookRequest();
        mockBookRequest.setBookId(1);
        mockBookRequests.add(mockBookRequest);
        request.setBooks(mockBookRequests);

        LoanEntity mockItem = new LoanEntity();
        mockItem.setLoanId(1);
        MemberEntity mockMember = new MemberEntity();
        mockMember.setMemberId(request.getMemberId());
        mockItem.setMember(mockMember);
        mockItem.setLoanDate(request.getLoanDate());
        mockItem.setReturnDate(request.getReturnDate());
        mockItem.setLoanStatus(LoanStatus.Return);
        mockItem.setCreatedBy(StaticVariable.CREATED_BY);
        mockItem.setCreatedDate(LocalDateTime.now());
        mockItem.setUpdatedBy(mockItem.getCreatedBy());
        mockItem.setUpdatedDate(mockItem.getCreatedDate());
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setBookId(1);
        books.add(book);
        mockItem.setBooks(books);

        LoanResponse mockResponse = new LoanResponse();
        mockResponse.setLoanId(mockItem.getLoanId());
        mockResponse.setMemberId(mockItem.getMember().getMemberId());
        mockResponse.setLoanDate(mockItem.getLoanDate());
        mockResponse.setLoanStatus(mockItem.getLoanStatus());
        mockResponse.setReturnDate(mockItem.getReturnDate());
        List<LoanBookResponse> mockBookResponses = new ArrayList<>();
        LoanBookResponse mockBookResponse = new LoanBookResponse();
        mockBookResponse.setBookId(book.getBookId());
        mockBookResponses.add(mockBookResponse);
        mockResponse.setBooks(mockBookResponses);


        Mockito.when(loanRepository.findById(1)).thenReturn(Optional.empty());


        Assertions.assertThrows(NotFoundException.class, () -> loanService.updateLoan(request, 1));


    }

    @Test
    void testUpdateLoanWithNotFoundMember() {
        LoanRequest request = new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(LocalDate.now());
        request.setLoanStatus(LoanStatus.Return.toString());
        request.setReturnDate(LocalDate.now());
        List<LoanBookRequest> mockBookRequests = new ArrayList<>();
        LoanBookRequest mockBookRequest = new LoanBookRequest();
        mockBookRequest.setBookId(1);
        mockBookRequests.add(mockBookRequest);
        request.setBooks(mockBookRequests);

        LoanEntity mockItem = new LoanEntity();
        mockItem.setLoanId(1);
        MemberEntity mockMember = new MemberEntity();
        mockMember.setMemberId(request.getMemberId());
        mockItem.setMember(mockMember);
        mockItem.setLoanDate(request.getLoanDate());
        mockItem.setReturnDate(request.getReturnDate());
        mockItem.setLoanStatus(LoanStatus.Return);
        mockItem.setCreatedBy(StaticVariable.CREATED_BY);
        mockItem.setCreatedDate(LocalDateTime.now());
        mockItem.setUpdatedBy(mockItem.getCreatedBy());
        mockItem.setUpdatedDate(mockItem.getCreatedDate());
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setBookId(1);
        books.add(book);
        mockItem.setBooks(books);

        LoanResponse mockResponse = new LoanResponse();
        mockResponse.setLoanId(mockItem.getLoanId());
        mockResponse.setMemberId(mockItem.getMember().getMemberId());
        mockResponse.setLoanDate(mockItem.getLoanDate());
        mockResponse.setLoanStatus(mockItem.getLoanStatus());
        mockResponse.setReturnDate(mockItem.getReturnDate());
        List<LoanBookResponse> mockBookResponses = new ArrayList<>();
        LoanBookResponse mockBookResponse = new LoanBookResponse();
        mockBookResponse.setBookId(book.getBookId());
        mockBookResponses.add(mockBookResponse);
        mockResponse.setBooks(mockBookResponses);


        Mockito.when(loanRepository.findById(1)).thenReturn(Optional.of(mockItem));
        Mockito.when(memberRepository.findById(1)).thenReturn(Optional.empty());


        Assertions.assertThrows(NotFoundException.class, () -> loanService.updateLoan(request, 1));


    }

    @Test
    void testUpdateLoanWithNotFoundBook() {
        LoanRequest request = new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(LocalDate.now());
        request.setLoanStatus(LoanStatus.Return.toString());
        request.setReturnDate(LocalDate.now());
        List<LoanBookRequest> mockBookRequests = new ArrayList<>();
        LoanBookRequest mockBookRequest = new LoanBookRequest();
        mockBookRequest.setBookId(1);
        mockBookRequests.add(mockBookRequest);
        request.setBooks(mockBookRequests);

        LoanEntity mockItem = new LoanEntity();
        mockItem.setLoanId(1);
        MemberEntity mockMember = new MemberEntity();
        mockMember.setMemberId(request.getMemberId());
        mockItem.setMember(mockMember);
        mockItem.setLoanDate(request.getLoanDate());
        mockItem.setReturnDate(request.getReturnDate());
        mockItem.setLoanStatus(LoanStatus.Pending);
        mockItem.setCreatedBy(StaticVariable.CREATED_BY);
        mockItem.setCreatedDate(LocalDateTime.now());
        mockItem.setUpdatedBy(mockItem.getCreatedBy());
        mockItem.setUpdatedDate(mockItem.getCreatedDate());
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setBookId(1);
        books.add(book);
        mockItem.setBooks(books);


        LoanResponse mockResponse = new LoanResponse();
        mockResponse.setLoanId(mockItem.getLoanId());
        mockResponse.setMemberId(mockItem.getMember().getMemberId());
        mockResponse.setLoanDate(mockItem.getLoanDate());
        mockResponse.setLoanStatus(mockItem.getLoanStatus());
        mockResponse.setReturnDate(mockItem.getReturnDate());
        List<LoanBookResponse> mockBookResponses = new ArrayList<>();
        LoanBookResponse mockBookResponse = new LoanBookResponse();
        mockBookResponse.setBookId(book.getBookId());
        mockBookResponses.add(mockBookResponse);
        mockResponse.setBooks(mockBookResponses);


        Mockito.when(bookRepository.findById(1)).thenReturn(Optional.empty());
        Mockito.when(loanRepository.findById(1)).thenReturn(Optional.of(mockItem));
        Mockito.when(memberRepository.findById(1)).thenReturn(Optional.of(mockMember));


        Assertions.assertThrows(RuntimeException.class, () -> loanService.updateLoan(request, 1));


    }

    @Test
    void testDeleteLoanSuccess() throws Exception {
        LoanEntity mockItem = new LoanEntity();
        mockItem.setLoanId(1);
        MemberEntity mockMember = new MemberEntity();
        mockMember.setMemberId(1);
        mockItem.setMember(mockMember);
        mockItem.setLoanDate(LocalDate.now());
        mockItem.setReturnDate(LocalDate.now());
        mockItem.setLoanStatus(LoanStatus.Loan);
        mockItem.setCreatedBy(StaticVariable.CREATED_BY);
        mockItem.setCreatedDate(LocalDateTime.now());
        mockItem.setUpdatedBy(mockItem.getCreatedBy());
        mockItem.setUpdatedDate(mockItem.getCreatedDate());
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setBookId(1);
        books.add(book);
        mockItem.setBooks(books);

        List<BookEntity> outBooks = new ArrayList<>();
        BookEntity outBook = new BookEntity();
        outBook.setBookId(1);
        outBook.setBookStatus(BookStatus.Pending);
        outBooks.add(outBook);

        Mockito.when(loanRepository.findById(1)).thenReturn(Optional.of(mockItem));
        Mockito.when(bookRepository.saveAll(books)).thenReturn(outBooks);

        DeleteResponse response = loanService.deleteLoan(1);

        Assertions.assertEquals(Message.DELETE_LOAN_SUCCESS, response.getMessage());
    }


    @Test
    void testDeleteLoanNotFound() {
        LoanEntity mockItem = new LoanEntity();
        mockItem.setLoanId(1);
        MemberEntity mockMember = new MemberEntity();
        mockMember.setMemberId(1);
        mockItem.setMember(mockMember);
        mockItem.setLoanDate(LocalDate.now());
        mockItem.setReturnDate(LocalDate.now());
        mockItem.setLoanStatus(LoanStatus.Loan);
        mockItem.setCreatedBy(StaticVariable.CREATED_BY);
        mockItem.setCreatedDate(LocalDateTime.now());
        mockItem.setUpdatedBy(mockItem.getCreatedBy());
        mockItem.setUpdatedDate(mockItem.getCreatedDate());
        List<BookEntity> books = new ArrayList<>();
        BookEntity book = new BookEntity();
        book.setBookId(1);
        books.add(book);
        mockItem.setBooks(books);


        Mockito.when(loanRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> loanService.deleteLoan(1));
    }
}
