package com.abit8.jdbc.entity;

import java.util.Objects;

public class Ticket {
    private Long id;
    private Long passport_no;
    private String passenger_name;
    private Flight flight;
    private Long seat_no;

    public Ticket() {
    }

    public Ticket(Long id, Long passport_no, String passenger_name, Flight flight, Long seat_no) {
        this.id = id;
        this.passport_no = passport_no;
        this.passenger_name = passenger_name;
        this.flight = flight;
        this.seat_no = seat_no;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPassport_no() {
        return passport_no;
    }

    public void setPassport_no(Long passport_no) {
        this.passport_no = passport_no;
    }

    public String getPassenger_name() {
        return passenger_name;
    }

    public void setPassenger_name(String passenger_name) {
        this.passenger_name = passenger_name;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Long getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(Long seat_no) {
        this.seat_no = seat_no;
    }

    @Override
    public String toString() {
        return "Ticket{" +
               "id=" + id +
               ", passport_no=" + passport_no +
               ", passenger_name='" + passenger_name + '\'' +
               ", flight_id=" + flight +
               ", seat_no=" + seat_no +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(passport_no, ticket.passport_no) && Objects.equals(passenger_name, ticket.passenger_name) && Objects.equals(flight, ticket.flight) && Objects.equals(seat_no, ticket.seat_no);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passport_no, passenger_name, flight, seat_no);
    }
}
