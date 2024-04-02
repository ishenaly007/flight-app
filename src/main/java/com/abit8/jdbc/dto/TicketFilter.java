package com.abit8.jdbc.dto;

public record TicketFilter(String passengerName, Long seatNo, int limit, int offset) {
}
