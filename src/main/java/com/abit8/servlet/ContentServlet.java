package com.abit8.servlet;

import com.abit8.jdbc.utils.JspHelper;
import com.abit8.service.FlightService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/content")
public class ContentServlet extends HttpServlet {

    private final FlightService flightService = FlightService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var flights = flightService.findAll();
        req.setAttribute("flights", flights);
        req.getRequestDispatcher(JspHelper.getPath("content")).forward(req, resp);
    }
}
