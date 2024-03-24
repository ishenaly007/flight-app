package com.abit8.jdbc.dao;

import com.abit8.jdbc.entity.Ticket;
import com.abit8.jdbc.exceptions.DaoException;
import com.abit8.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDao {
    private static final TicketDao INSTANCE = new TicketDao();

    private final static String SAVE_SQL = """
            INSERT INTO flights.ticket
            (passport_no, passenger_name, flight_id, seat_no)
            VALUES (?,?,?,?);
            """;
    private final static String DELETE_SQL = """
            DELETE FROM flights.ticket
            WHERE id = ?;
            """;
    private final static String FIND_ALL_SQL = """
            SELECT id, passport_no, passenger_name, flight_id, seat_no
            FROM flights.ticket
            """;
    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """        
            WHERE id = ?
            """;

    private final static String UPDATE_SQL = """
            UPDATE flights.ticket t
            SET passenger_name = ?,
                passport_no = ?,
                flight_id = ?,
                seat_no = ?
            WHERE id = ?;
            """;

    public boolean update(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, ticket.getPassenger_name());
            statement.setLong(2, ticket.getPassport_no());
            statement.setLong(3, ticket.getFlight_id());
            statement.setLong(4, ticket.getSeat_no());
            statement.setLong(4, ticket.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Ticket> findById(int id) {//чтобы null не возвращать, надо это получше изучить
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            Ticket ticket = null;
            if (result.next()) {
                ticket = buildTicket(result);
            }
            return Optional.ofNullable(ticket);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Ticket> tickets = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                tickets.add(
                        buildTicket(result)
                );
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Ticket buildTicket(ResultSet result) throws SQLException {
        return new Ticket(result.getLong("id"),
                result.getLong("passport_no"),
                result.getString("passenger_name"),
                result.getLong("flight_id"),
                result.getLong("seat_no"));
    }

    public Ticket save(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, ticket.getPassport_no());
            statement.setString(2, ticket.getPassenger_name());
            statement.setLong(3, ticket.getFlight_id());
            statement.setLong(4, ticket.getSeat_no());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                ticket.setId(keys.getLong("id"));
            }
            return ticket;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }

    private TicketDao() {
    }
}
