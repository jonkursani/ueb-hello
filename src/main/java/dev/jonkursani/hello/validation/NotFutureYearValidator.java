package dev.jonkursani.hello.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class NotFutureYearValidator implements ConstraintValidator<NotFutureYear, Integer> {
    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        // 1. viti eshte opsional: bosh = ne rregull
        if (year == null) {
            return true;
        }
        // 2. viti i sotem, jo 2026 i shkruar me dore
        int thisYear = Year.now().getValue();
        return year <= thisYear;
    }
}
