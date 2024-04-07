package com.abit8.jdbc.validator;

import com.abit8.jdbc.dto.CreateUserDto;
import com.abit8.jdbc.utils.Gender;
import com.abit8.jdbc.utils.LocalDateFormatter;
import com.abit8.jdbc.utils.Roles;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto userDto) {
        var validationResult = new ValidationResult();
        if (!LocalDateFormatter.isValid(userDto.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        if (Roles.find(userDto.getRole()).isEmpty()) {
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        if (Gender.find(userDto.getGender()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        if (userDto.getName().isEmpty()) {
            validationResult.add(Error.of("invalid.name", "Name is invalid"));
        }
        if (userDto.getPassword().isEmpty()) {
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
