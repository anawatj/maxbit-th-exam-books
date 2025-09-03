package com.exam.book.dto.request;

import com.exam.book.constant.ValidationError;
import com.exam.book.enums.Role;
import com.exam.book.validators.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberRequest {

    @NotBlank(message = ValidationError.MEMBER_NAME_IS_NOT_EMPTY)
    private String memberName;

    @NotBlank(message = ValidationError.MEMBER_LASTNAME_IS_NOT_EMPTY)
    private String memberLastname;

    @Email(message = ValidationError.EMAIL_IS_INVALID)
    private String email;

    @NotBlank(message = ValidationError.PASSWORD_IS_NOT_EMPTY)
    private String password;

    @ValidRole(enumClass = Role.class,message = ValidationError.ROLE_IS_NOT_EMPTY)
    private String role;
}
