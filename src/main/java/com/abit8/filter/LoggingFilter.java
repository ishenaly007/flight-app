package com.abit8.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*")
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.getParameterMap().forEach((k, a) -> System.out.println(k + " : " + Arrays.toString(a)));
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
