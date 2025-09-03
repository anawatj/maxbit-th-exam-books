package com.exam.book.validators;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.*;
import java.util.Arrays;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {
    private Class<? extends Enum<?>> enumClass;
    @Override
    public void initialize(ValidRole constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true; // ปล่อยให้ @NotNull ตรวจเอง

        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equalsIgnoreCase(value));
    }
}
