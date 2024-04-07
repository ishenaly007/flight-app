package com.abit8.jdbc.dto;

import com.abit8.jdbc.utils.Gender;
import com.abit8.jdbc.utils.Roles;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    public Long id;
    public String name;
    public LocalDate birthday;
    public String email;
    public Roles role;
    public Gender gender;
}