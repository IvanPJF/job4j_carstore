package ru.job4j.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class AuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (!request.getRequestURI().contains("/sign-in")) {
            HttpSession session = request.getSession();
            if (Objects.isNull(session.getAttribute("advertiser"))) {
                ((HttpServletResponse) resp).sendRedirect(String.format("%s/sign-in", request.getContextPath()));
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
