package com.abit8.jdbc.mapper;

import com.abit8.jdbc.dto.UserDto;
import com.abit8.jdbc.entity.User;
import com.abit8.jdbc.utils.Gender;
import com.abit8.jdbc.utils.LocalDateFormatter;
import com.abit8.jdbc.utils.Roles;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements Mapper<UserDto, User> {
    private static final UserMapper INSTANCE = new UserMapper();


    public static UserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .id(Long.valueOf(user.getId()))
                .name(user.getName())
                .birthday(LocalDateFormatter.format(String.valueOf(user.getBirthday())))
                .email(user.getEmail())
                .role(Roles.valueOf(String.valueOf(user.getRole())))
                .gender(Gender.valueOf(String.valueOf(user.getGender())))
                .build();
    }
}
