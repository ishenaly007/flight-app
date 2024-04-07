package com.abit8.service;

import com.abit8.jdbc.dao.UserDao;
import com.abit8.jdbc.dto.CreateUserDto;
import com.abit8.jdbc.dto.UserDto;
import com.abit8.jdbc.exceptions.ValidationException;
import com.abit8.jdbc.mapper.CreateUserMapper;
import com.abit8.jdbc.mapper.UserMapper;
import com.abit8.jdbc.validator.CreateUserValidator;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator userValidator = CreateUserValidator.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    public Optional<UserDto> login(String email, String password) {
        return userDao.getByEmailAndPassword(email, password).map(userMapper::mapFrom);
    }

    public Integer create(CreateUserDto createUserDto) {
        var validationResult = userValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var user = createUserMapper.mapFrom(createUserDto);
        userDao.save(user);
        return user.getId();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}