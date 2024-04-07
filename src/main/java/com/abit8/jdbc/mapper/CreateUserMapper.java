package com.abit8.jdbc.mapper;

import com.abit8.jdbc.dto.CreateUserDto;
import com.abit8.jdbc.entity.User;
import com.abit8.jdbc.utils.Gender;
import com.abit8.jdbc.utils.LocalDateFormatter;
import com.abit8.jdbc.utils.Roles;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserMapper implements Mapper<User, CreateUserDto> {
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();
    @Override
    public User mapFrom(CreateUserDto createUserDto) {
        return User.builder()
                .name(createUserDto.getName())
                .birthday(LocalDateFormatter.format(createUserDto.getBirthday()))
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .role(Roles.valueOf(createUserDto.getRole()))
                .gender(Gender.valueOf(createUserDto.getGender()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
