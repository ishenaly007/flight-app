package com.abit8.jdbc.dao;

import com.abit8.jdbc.dto.TicketFilter;
import com.abit8.jdbc.entity.Ticket;
import com.abit8.jdbc.exceptions.DaoException;
import com.abit8.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketDao implements Dao<Long, Ticket> {
    private static final TicketDao INSTANCE = new TicketDao();
    private static final FlightDao flightDao = FlightDao.getInstance();

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
            SELECT t.id, t.passport_no, t.passenger_name, t.flight_id, t.seat_no,
            f.flight_no,
            f.departure_date,
            f.departure_airport_code,
            f.arrival_date,
            f.arrival_airport_code,
            f.aircraft_id,
            f.status
            FROM flights.ticket t
            JOIN flights.flight f on f.id = t.flight_id
            """;
    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """        
            WHERE t.id = ?
            """;

    private final static String FIND_BY_FLIGHT_ID_SQL = FIND_ALL_SQL + """        
            WHERE t.flight_id = ?
            """;

    private final static String UPDATE_SQL = """
            UPDATE flights.ticket t
            SET passenger_name = ?,
                passport_no = ?,
                flight_id = ?,
                seat_no = ?
            WHERE id = ?;
            """;

    public List<Ticket> findByFlightId(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_FLIGHT_ID_SQL)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (result.next()) {
                tickets.add(buildTicket(result));
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAll(TicketFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.passengerName() != null) {
            parameters.add(filter.passengerName());
            whereSql.add("passenger_name = ?");
        }
        if (filter.seatNo() != null) {
            parameters.add(filter.seatNo());
            whereSql.add("seat_no = ?");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        var where = whereSql.stream().collect(Collectors.joining(
                " AND ",
                parameters.size() > 2 ? " WHERE " : " ",
                " LIMIT ? OFFSET ? "));
        var sql = FIND_ALL_SQL + where;
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {
            List<Ticket> tickets = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(statement);
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

    public boolean update(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, ticket.getPassenger_name());
            statement.setLong(2, ticket.getPassport_no());
            statement.setLong(3, ticket.getFlight().getId());
            statement.setLong(4, ticket.getSeat_no());
            statement.setLong(5, ticket.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Ticket> findById(Long id) {//чтобы null не возвращать, надо это получше изучить
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
        /*var flight = new Flight(result.getLong("flight_id"),
                result.getLong("flight_no"),
                result.getTimestamp("departure_date").toLocalDateTime(),
                result.getString("departure_airport_code"),
                result.getTimestamp("arrival_date").toLocalDateTime(),
                result.getString("arrival_airport_code"),
                result.getInt("aircraft_id"),
                FlightStatus.valueOf(result.getString("status")));*/

        return new Ticket(result.getLong("id"),
                result.getLong("passport_no"),
                result.getString("passenger_name"),
                flightDao.findById(result.getLong("flight_id"),
                        result.getStatement().getConnection()).orElse(null),
                result.getLong("seat_no"));
    }

    public Ticket save(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, ticket.getPassport_no());
            statement.setString(2, ticket.getPassenger_name());
            statement.setLong(3, ticket.getFlight().getId());
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
