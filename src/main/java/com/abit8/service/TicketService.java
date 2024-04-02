package com.abit8.service;

import com.abit8.jdbc.dao.TicketDao;
import com.abit8.jdbc.dto.TicketDto;

import java.util.List;
import java.util.stream.Collectors;

public class TicketService {
    private static final TicketService INSTANCE = new TicketService();
    private final TicketDao ticketDao = TicketDao.getInstance();

    public List<TicketDto> findByFlightId(Long id) {
        return ticketDao.findByFlightId(id).stream().map(ticket ->
                        new TicketDto(
                                ticket.getId(),
                                ticket.getFlight().getId(),
                                (ticket.getPassenger_name() + " " + ticket.getSeat_no())))
                .collect(Collectors.toList());
    }

    public static TicketService getInstance() {
        return INSTANCE;
    }

    private TicketService() {

    }
}
