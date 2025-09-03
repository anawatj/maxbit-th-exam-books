package com.exam.book.controllers;

import com.exam.book.constant.Message;
import com.exam.book.dto.request.LoanRequest;
import com.exam.book.dto.response.DeleteResponse;
import com.exam.book.dto.response.LoanResponse;
import com.exam.book.enums.LoanStatus;
import com.exam.book.exceptions.NotFoundException;
import com.exam.book.services.LoanService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoanController.class)
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // mock Service และ inject เข้า Controller
    private LoanService loanService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testGetAllLoanSuccess() throws Exception {
        List<LoanResponse> responses = new ArrayList<>();
        LoanResponse response = new LoanResponse();
        response.setLoanId(1);
        response.setLoanDate(LocalDate.now());
        response.setReturnDate(LocalDate.now());
        response.setMemberId(1);
        response.setLoanStatus(LoanStatus.Loan);
        responses.add(response);

        Mockito.when(loanService.getAllLoan()).thenReturn(responses);

        mockMvc.perform(get("/loans"))
                .andExpect(status().isOk());

    }

    @Test
    void testGetAllLoanNotFound() throws Exception{

        Mockito.when(loanService.getAllLoan()).thenThrow(new NotFoundException(Message.LOAN_NOT_FOUND));

        mockMvc.perform(get("/loans"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllLoanInternalServerError() throws Exception{

        Mockito.when(loanService.getAllLoan()).thenThrow(new Exception());

        mockMvc.perform(get("/loans"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetLoanSuccess() throws Exception {

        LoanResponse response = new LoanResponse();
        response.setLoanId(1);
        response.setLoanDate(LocalDate.now());
        response.setReturnDate(LocalDate.now());
        response.setMemberId(1);
        response.setLoanStatus(LoanStatus.Loan);


        Mockito.when(loanService.getLoan(1)).thenReturn(response);

        mockMvc.perform(get("/loans/{id}",1))
                .andExpect(status().isOk());

    }

    @Test
    void testGetLoanNotFound() throws Exception{

        Mockito.when(loanService.getLoan(1)).thenThrow(new NotFoundException(Message.LOAN_NOT_FOUND));

        mockMvc.perform(get("/loans/{id}",1))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetLoanInternalServerError() throws Exception{

        Mockito.when(loanService.getLoan(1)).thenThrow(new Exception());

        mockMvc.perform(get("/loans/{id}",1))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testCreateLoanSuccess() throws  Exception{
        LoanRequest request =new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(LocalDate.now());
        request.setReturnDate(LocalDate.now());
        request.setLoanStatus(LoanStatus.Loan.toString());
        LoanResponse response = new LoanResponse();
        response.setLoanId(1);
        response.setLoanDate(LocalDate.now());
        response.setReturnDate(LocalDate.now());
        response.setMemberId(1);
        response.setLoanStatus(LoanStatus.Loan);

        Mockito.when(loanService.createLoan(request)).thenReturn(response);

        mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateLoanBadRequest() throws  Exception{
        LoanRequest request =new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(null);
        request.setReturnDate(null);
        request.setLoanStatus(null);


        Mockito.when(loanService.createLoan(request)).thenThrow(new ValidationException(""));

        mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateLoanNotFound() throws  Exception{
        LoanRequest request =new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(null);
        request.setReturnDate(null);
        request.setLoanStatus(null);


        Mockito.when(loanService.createLoan(request)).thenThrow(new NotFoundException(Message.LOAN_NOT_FOUND));

        mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateLoanInternalServerError() throws  Exception{
        LoanRequest request =new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(null);
        request.setReturnDate(null);
        request.setLoanStatus(null);


        Mockito.when(loanService.createLoan(request)).thenThrow(new Exception(""));

        mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testUpdateLoanSuccess() throws  Exception{
        LoanRequest request =new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(LocalDate.now());
        request.setReturnDate(LocalDate.now());
        request.setLoanStatus(LoanStatus.Loan.toString());
        LoanResponse response = new LoanResponse();
        response.setLoanId(1);
        response.setLoanDate(LocalDate.now());
        response.setReturnDate(LocalDate.now());
        response.setMemberId(1);
        response.setLoanStatus(LoanStatus.Loan);

        Mockito.when(loanService.updateLoan(request,1)).thenReturn(response);

        mockMvc.perform(put("/loans/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateLoanBadRequest() throws  Exception{
        LoanRequest request =new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(null);
        request.setReturnDate(null);
        request.setLoanStatus(null);


        Mockito.when(loanService.updateLoan(request,1)).thenThrow(new ValidationException(""));

        mockMvc.perform(put("/loans/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateLoanNotFound() throws  Exception{
        LoanRequest request =new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(null);
        request.setReturnDate(null);
        request.setLoanStatus(null);


        Mockito.when(loanService.updateLoan(request,1)).thenThrow(new NotFoundException(Message.LOAN_NOT_FOUND));

        mockMvc.perform(put("/loans/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateLoanInternalServerError() throws  Exception{
        LoanRequest request =new LoanRequest();
        request.setMemberId(1);
        request.setLoanDate(null);
        request.setReturnDate(null);
        request.setLoanStatus(null);


        Mockito.when(loanService.updateLoan(request,1)).thenThrow(new Exception(""));

        mockMvc.perform(put("/loans/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testDeleteLoanSuccess() throws Exception {


        DeleteResponse response = new DeleteResponse();
        response.setMessage(Message.LOAN_NOT_FOUND);

        Mockito.when(loanService.deleteLoan(1)).thenReturn(response);

        mockMvc.perform(delete("/loans/{id}",1))
                .andExpect(status().isOk());

    }

    @Test
    void testDeleteLoanNotFound() throws Exception{

        Mockito.when(loanService.deleteLoan(1)).thenThrow(new NotFoundException(Message.LOAN_NOT_FOUND));

        mockMvc.perform(delete("/loans/{id}",1))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteLoanInternalServerError() throws Exception{

        Mockito.when(loanService.deleteLoan(1)).thenThrow(new Exception());

        mockMvc.perform(delete("/loans/{id}",1))
                .andExpect(status().isInternalServerError());
    }
}
