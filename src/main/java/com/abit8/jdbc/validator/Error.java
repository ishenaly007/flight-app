package com.abit8.jdbc.validator;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
    String code;
    String message;
}