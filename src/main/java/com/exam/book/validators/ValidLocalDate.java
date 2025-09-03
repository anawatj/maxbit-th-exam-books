package com.exam.book.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LocalDateValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLocalDate {
String message() default "Invalid date";
Class<?>[] groups() default {};
Class<? extends Payload>[] payload() default {};

boolean past() default false;     // อนุญาตให้เป็นอดีต
boolean future() default false;   // อนุญาตให้เป็นอนาคต
boolean present() default false;  // อนุญาตให้เป็นวันนี้
        }
