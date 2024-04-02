package com.abit8.jdbc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    public Long id;
    public String email;
}
