package ru.job4j.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.model.Advert;
import ru.job4j.model.Advertiser;
import ru.job4j.service.Service;
import ru.job4j.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdvertServlet extends HttpServlet {

    private static final Service SERVICE = ValidateService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String action = req.getParameter("action");
        Collection<Advert> adverts = null;
        if ("getByAdvertiser".equals(action)) {
            Advertiser advertiser = (Advertiser) req.getSession().getAttribute("advertiser");
            adverts = SERVICE.findAdvertsByAdvertiser(advertiser);
        } else {
            adverts = SERVICE.allActiveAdverts();
        }
        try (final PrintWriter writer = resp.getWriter()) {
            new ObjectMapper().writeValue(writer, adverts);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String action = req.getParameter("action");
        String advertsJson = req.getParameter("adverts");
        boolean result = false;
        if ("changeAdvertsStatus".equals(action) && Objects.nonNull(advertsJson)) {
            HttpSession session = req.getSession();
            Advertiser advertiser = (Advertiser) session.getAttribute("advertiser");
            ObjectMapper mapper = new ObjectMapper();
            Map<Integer, Advert> adverts = mapper.readValue(advertsJson, new TypeReference<>() {
            });
            result = SERVICE.changeAdvertsStatus(adverts);
        }
        if (!result) {
            resp.sendError(500, "Statuses failed to change");
        }
    }
}
