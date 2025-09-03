package com.exam.book.dto.response;

import com.exam.book.constant.ValidationError;
import com.exam.book.enums.Role;
import com.exam.book.validators.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberResponse {
    private Integer memberId;
    private String memberName;
    private String memberLastname;
    private String email;
    private String password;
    private String role;
}
