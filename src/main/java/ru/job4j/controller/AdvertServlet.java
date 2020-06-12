package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.model.Advert;
import ru.job4j.service.Service;
import ru.job4j.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class AdvertServlet extends HttpServlet {

    private static final Service SERVICE = ValidateService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
//        boolean ajax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
//        if (!ajax) {
//            resp.sendRedirect(String.format("%s/", req.getContextPath()));
//            return;
//        }
        try (final PrintWriter writer = resp.getWriter()) {
            new ObjectMapper().writeValue(writer, SERVICE.allAdverts());
        }
    }
}
