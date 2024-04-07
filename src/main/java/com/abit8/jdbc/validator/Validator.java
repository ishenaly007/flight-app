package com.abit8.jdbc.validator;

public interface Validator<T> {
    ValidationResult isValid(T object);
}
