package com.exam.book.services;

import com.exam.book.constant.Message;
import com.exam.book.constant.StaticVariable;
import com.exam.book.dto.request.LoanRequest;
import com.exam.book.dto.response.DeleteResponse;
import com.exam.book.dto.response.LoanResponse;
import com.exam.book.entities.BookEntity;
import com.exam.book.entities.LoanEntity;
import com.exam.book.entities.MemberEntity;
import com.exam.book.enums.BookStatus;
import com.exam.book.enums.LoanStatus;
import com.exam.book.exceptions.BadRequestException;
import com.exam.book.exceptions.NotFoundException;
import com.exam.book.mappers.LoanMapper;
import com.exam.book.repositories.BookRepository;
import com.exam.book.repositories.LoanRepository;
import com.exam.book.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LoanMapper mapper;

    public List<LoanResponse> getAllLoan() throws Exception{

        List<LoanEntity> loanEntities = loanRepository.findAll();
        if(loanEntities.isEmpty()){
            throw new NotFoundException(Message.LOAN_NOT_FOUND);
        }
        return getLoanListResponse(loanEntities);
    }
    public LoanResponse getLoan(Integer loanId) throws Exception {
        LoanEntity loanEntity = loanRepository.findById(loanId).orElseThrow(()->new NotFoundException(Message.LOAN_NOT_FOUND));
        return getLoanResponse(loanEntity);
    }
    @Transactional(rollbackFor = Exception.class)
    public LoanResponse createLoan(LoanRequest loanRequest) throws Exception{
        LoanEntity loanEntity =  getCreateLoanEntity(loanRequest);
        List<String> bookErrors = new ArrayList<>();
        if(loanEntity.getLoanStatus()==LoanStatus.Loan){
            loanEntity.getBooks().forEach(bookEntity -> {
                if(bookEntity.getBookStatus()==BookStatus.Pending){
                    bookEntity.setBookStatus(BookStatus.Loan);
                }else{
                    bookErrors.add(Integer.toString(bookEntity.getBookId())+" "+Message.BOOK_IS_LOANED);
                }

            });
            if(!bookErrors.isEmpty()){
                throw new BadRequestException(String.join("\\n",bookErrors));
            }
        }

        final LoanEntity savedLoan= loanRepository.save(loanEntity);
        return getLoanResponse(savedLoan);
    }
    @Transactional(rollbackFor = Exception.class)
    public LoanResponse updateLoan(LoanRequest request,Integer loanId) throws Exception{
        LoanEntity loanEntity =  getUpdateLoanEntity(request, loanId);
        List<String> bookErrors = new ArrayList<>();
        if(loanEntity.getLoanStatus()==LoanStatus.Return){
            loanEntity.getBooks().forEach(bookEntity -> {
                bookEntity.setBookStatus(BookStatus.Pending);
            });
        }else if(loanEntity.getLoanStatus()==LoanStatus.Loan){

            loanEntity.getBooks().forEach(bookEntity -> {
                if(bookEntity.getBookStatus()==BookStatus.Pending){
                    bookEntity.setBookStatus(BookStatus.Loan);
                }else{
                    bookErrors.add(Integer.toString(bookEntity.getBookId())+" "+Message.BOOK_IS_LOANED);
                }

            });
            if(!bookErrors.isEmpty()){
                throw new BadRequestException(String.join("\\n",bookErrors));
            }

        }
        final LoanEntity savedLoan = loanRepository.save(loanEntity);
        return getLoanResponse(savedLoan);
    }
    @Transactional(rollbackFor = Exception.class)
    public DeleteResponse deleteLoan(Integer loanId) throws Exception{
        LoanEntity loanEntity=loanRepository.findById(loanId).orElseThrow(()->new NotFoundException(Message.LOAN_NOT_FOUND));
        loanEntity.getBooks().forEach(bookEntity -> {
            bookEntity.setBookStatus(BookStatus.Pending);
        });
        bookRepository.saveAll(loanEntity.getBooks());
        loanRepository.delete(loanEntity);
        DeleteResponse response = new DeleteResponse();
        response.setMessage(Message.DELETE_LOAN_SUCCESS);
        return response;
    }

    private LoanEntity getUpdateLoanEntity(LoanRequest request,Integer loanId) throws NotFoundException {
        LoanEntity loanEntity=loanRepository.findById(loanId).orElseThrow(()->new NotFoundException(Message.LOAN_NOT_FOUND));
        MemberEntity memberEntity=memberRepository.findById(request.getMemberId()).orElseThrow(()->new NotFoundException(Message.MEMBER_NOT_FOUND));
        loanEntity.setLoanDate(request.getLoanDate());
        loanEntity.setReturnDate(request.getReturnDate());
        loanEntity.setMember(memberEntity);
        loanEntity.setLoanStatus(LoanStatus.valueOf(request.getLoanStatus()));
        loanEntity.setBooks(request.getBooks().stream().map(bookRequest->{
            Optional<BookEntity> optBookEntity = bookRepository.findById(bookRequest.getBookId());
            if(optBookEntity.isEmpty()){
                try {
                    throw new NotFoundException(Message.BOOK_NOT_FOUND);
                } catch (NotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            return optBookEntity.get();
        }).collect(Collectors.toList()));
        loanEntity.setUpdatedBy(StaticVariable.UPDATED_BY);
        loanEntity.setUpdatedDate(LocalDateTime.now());
        return loanEntity;
    }

    private LoanEntity getCreateLoanEntity(LoanRequest loanRequest) throws NotFoundException {
        LoanEntity loanEntity = mapper.toEntity(loanRequest);
        MemberEntity memberEntity = memberRepository.findById(loanRequest.getMemberId()).orElseThrow(()->new NotFoundException(Message.MEMBER_NOT_FOUND));
        loanEntity.setBooks(loanRequest.getBooks().stream().map(bookRequest->{
            try {
                BookEntity bookEntity = bookRepository.findById(bookRequest.getBookId()).orElseThrow(()->new NotFoundException(Message.BOOK_NOT_FOUND));
                return bookEntity;
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList()));
        loanEntity.setMember(memberEntity);
        loanEntity.setCreatedBy(StaticVariable.CREATED_BY);
        loanEntity.setCreatedDate(LocalDateTime.now());
        loanEntity.setUpdatedBy(loanEntity.getCreatedBy());
        loanEntity.setUpdatedDate(loanEntity.getCreatedDate());
        return loanEntity;
    }

    private LoanResponse getLoanResponse(LoanEntity loanEntity) {
        LoanResponse response = mapper.toResponse(loanEntity);
        response.setMemberId(loanEntity.getMember().getMemberId());
        response.setBooks(loanEntity.getBooks().stream().map(mapper::toBookResponse).toList());
        return response;
    }

    private List<LoanResponse> getLoanListResponse(List<LoanEntity> loanEntities) {
        return loanEntities.stream().map(loanEntity -> {
            LoanResponse dto = mapper.toResponse(loanEntity);
            dto.setBooks(loanEntity.getBooks().stream().map(mapper::toBookResponse).toList());
            dto.setMemberId(loanEntity.getMember().getMemberId());
            return dto;
        }).toList();
    }
}
