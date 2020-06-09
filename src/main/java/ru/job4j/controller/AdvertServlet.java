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

public class AdvertServlet extends HttpServlet {

    private static final Service SERVICE = ValidateService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (final PrintWriter writer = resp.getWriter()) {
            new ObjectMapper().writeValue(writer, SERVICE.allAdverts());
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String action = req.getParameter("action");
        String advertJSON = req.getParameter("advert");
        ObjectMapper jsonMapper = new ObjectMapper();
        Advert advert = jsonMapper.readValue(advertJSON, Advert.class);
        SERVICE.addAdvert(advert);
        try (final PrintWriter writer = resp.getWriter()) {
            jsonMapper.writeValue(writer, advert);
        }
    }
}
