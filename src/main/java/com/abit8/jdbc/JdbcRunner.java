package com.abit8.jdbc;

import com.abit8.jdbc.dao.TicketDao;
import com.abit8.jdbc.utils.ConnectionManager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

        /*System.out.println("Welcome to server...");
        System.out.println("Enter your name..");
        Scanner scan = new Scanner(System.in);
        String username = scan.nextLine();
        System.out.println("There are your tables..");
        System.out.println("1.aircraft " + "| 2.airport " + "| 3.flight " + "| 4.seat " + "| 5.ticket ");
        boolean tables = true;
        while (tables) {*/

           /* System.out.println("Choose table(1,2,3..");
            System.out.println("Exit(0..");
            Scanner scan2 = new Scanner(System.in);
            int tableNum = scan.nextInt();
            String table = "";
            switch (tableNum) {
                case 1:
                    table = "aircraft";
                    break;
                case 2:
                    table = "airport";
                    break;
                case 3:
                    table = "flight";
                    break;
                case 4:
                    table = "seat";
                    break;
                case 5:
                    table = "ticket";
                    break;
                case 0:
                    tables = false;
                    break;
            }
            String sqlTable = """
                    SELECT * FROM flights.%s
                    """.formatted(table);
            try (var connection = ConnectionManager.open();
                 var statement = connection.prepareStatement(sqlTable)) {
                var result = statement.executeQuery();
                while (result.next()) {
                    System.out.println(result.getRow());
                }
            }
        }*/
        /*DriverManager.getConnection(url, user, passw);*/

        //обычно надо закрывать connection поэтому просто закинем в try/catch
       /* String sql = """
                SELECT * FROM flights.ticket;
                """;
        try (var connection = ConnectionManager.get();
             var statement = connection.createStatement()) {
            statement.setFetchSize(2);//чтобы память не перегружать, грузит по 2 элем
            statement.setMaxRows(2);//максимально 2 элемента наверно, 2 строки
            statement.setQueryTimeout(1);//сколько сек ждать запрос от сервера, чтобы пробросить исключение

            var result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.println(result.getString("passenger_name"));
                System.out.println(result.getInt("flight_id"));
                System.out.println("-----------------");
            }
        }
        System.out.println(getUserByFlightId(6));
        System.out.println(getFlightsBetween(LocalDate.of(2022, 1, 1).atStartOfDay(),
                LocalDate.of(2023, 1, 1).atStartOfDay()));
        checkMetaData();*/

        var ticketDao = TicketDao.getInstance();

        System.out.println(ticketDao.findAll());
        System.out.println(ticketDao.findById(34).get());
    }

    public static List<String> getUserByFlightId(int id) {
        String sql2 = """
                SELECT * FROM flights.ticket t
                where flight_id = ?;
                """;
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql2)) {
            statement.setInt(1, id);
            var result = statement.executeQuery();
            List<String> results = new ArrayList<>();
            while (result.next()) {
                results.add(result.getString("passenger_name"));
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) {
        String sql2 = """
                SELECT * FROM flights.flight f
                WHERE f.departure_date between ? and ?
                ;
                """;
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql2)) {
            statement.setTimestamp(1, Timestamp.valueOf(start));
            statement.setTimestamp(2, Timestamp.valueOf(end));
            var result = statement.executeQuery();
            List<Long> results = new ArrayList<>();
            while (result.next()) {
                results.add(result.getLong("id"));
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkMetaData() {
        try (var connection = ConnectionManager.get()) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs();
            while (catalogs.next())
                System.out.println(catalogs.getString(1));
            //Alt+7 all methods in class or just "structure"
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
