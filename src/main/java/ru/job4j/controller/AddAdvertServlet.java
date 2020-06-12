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

public class AddAdvertServlet extends HttpServlet {

    private static final Service SERVICE = ValidateService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/html/addAdvert.html").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String advertJSON = req.getParameter("advert");
        ObjectMapper mapper = new ObjectMapper();
        Advert advert = mapper.readValue(advertJSON, Advert.class);
        SERVICE.addAdvert(advert);
        try (final PrintWriter writer = resp.getWriter()) {
            mapper.writeValue(writer, advert);
        }
    }
}
