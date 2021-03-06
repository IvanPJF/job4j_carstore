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
        HttpServletRequest httpReq = (HttpServletRequest) req;
        if (!httpReq.getRequestURI().contains("/signin")) {
            HttpSession session = httpReq.getSession();
            if (Objects.isNull(session.getAttribute("advertiser"))) {
                String redirectPath = String.format("%s/signin", httpReq.getContextPath());
                ((HttpServletResponse) resp).sendRedirect(redirectPath);
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
