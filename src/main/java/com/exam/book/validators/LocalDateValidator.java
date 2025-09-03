package com.exam.book.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class LocalDateValidator implements ConstraintValidator<ValidLocalDate, LocalDate> {

    private boolean past;
    private boolean future;
    private boolean present;

    @Override
    public void initialize(ValidLocalDate constraintAnnotation) {
        this.past = constraintAnnotation.past();
        this.future = constraintAnnotation.future();
        this.present = constraintAnnotation.present();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) return true; // ปล่อยให้ @NotNull ตรวจเอง

        LocalDate today = LocalDate.now();

        if (past && value.isBefore(today)) return true;
        if (future && value.isAfter(today)) return true;
        if (present && value.isEqual(today)) return true;

        // ถ้าไม่เข้าเงื่อนไขไหนเลย ถือว่าไม่ valid
        return false;
    }
}
