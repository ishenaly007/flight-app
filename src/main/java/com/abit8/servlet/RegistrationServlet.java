package com.abit8.servlet;

import com.abit8.jdbc.dto.CreateUserDto;
import com.abit8.jdbc.exceptions.ValidationException;
import com.abit8.jdbc.utils.Gender;
import com.abit8.jdbc.utils.JspHelper;
import com.abit8.jdbc.utils.Roles;
import com.abit8.jdbc.utils.UrlPath;
import com.abit8.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.abit8.jdbc.utils.UrlPath.REGISTRATION;

@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Roles.values());
        req.setAttribute("genders", Gender.values());
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write(req.getParameter("name"));
        var userDto = CreateUserDto.builder()
                .name(req.getParameter("name"))
                .email(req.getParameter("email"))
                .birthday(req.getParameter("birthday"))
                .password(req.getParameter("password"))
                .role(req.getParameter("role"))
                .gender(req.getParameter("gender"))
                .build();

        try {
            userService.create(userDto);
            resp.sendRedirect("/login");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }

    }
}