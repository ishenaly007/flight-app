package com.abit8.jdbc.entity;

import com.abit8.jdbc.utils.Gender;
import com.abit8.jdbc.utils.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private LocalDate birthday;
    private String email;
    private String password;
    private Roles role;
    private Gender gender;
}
