package com.abit8.jdbc.dao;


import com.abit8.jdbc.entity.User;
import com.abit8.jdbc.exceptions.DaoException;
import com.abit8.jdbc.utils.ConnectionManager;
import com.abit8.jdbc.utils.Gender;
import com.abit8.jdbc.utils.Roles;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements Dao<Long, User> {
    private final static UserDao INSTANCE = new UserDao();

    private final static String SAVE_SQL = """
            INSERT INTO users
            (name, birthday, email, password, role, gender)
            VALUES (?,?,?,?,?,?);
            """;
    private final static String GET_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT * FROM users WHERE email = ? AND password = ?
            """;

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    public Optional<User> getByEmailAndPassword(String email, String password) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            statement.setObject(1, email);
            statement.setObject(2, password);
            User user = null;
            var result = statement.executeQuery();
            if (result.next()) {
                user = buildEntity(result);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private User buildEntity(ResultSet result) throws SQLException {
        return User.builder()
                .id(result.getObject("id", Integer.class))
                .name(result.getObject("name", String.class))
                .birthday(result.getObject("birthday", Date.class).toLocalDate())
                .email(result.getObject("email", String.class))
                .password(result.getObject("password", String.class))
                .role(Roles.find(result.getObject("role", String.class)).orElse(null))
                .gender(Gender.valueOf(result.getObject("gender", String.class)))
                .build();
    }

    @Override
    public User save(User user) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            statement.setObject(1, user.getName());
            statement.setObject(2, user.getBirthday());
            statement.setObject(3, user.getEmail());
            statement.setObject(4, user.getPassword());
            statement.setObject(5, user.getRole().name());
            statement.setObject(6, user.getGender().name());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                user.setId((int) keys.getLong("id"));
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}