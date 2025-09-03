package com.exam.book.validators;

import jakarta.validation.Payload;

public @interface ValidLoanStatus {
    String message() default "must be any of enum {enumClass}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();
}
