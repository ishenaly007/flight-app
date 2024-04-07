package com.abit8.jdbc.utils;

import java.util.Arrays;
import java.util.Optional;

public enum Roles {
    ADMIN, USER, MAMA, DADA;

    public static Optional<Roles> find(String role) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(role)).findFirst();
    }
}
