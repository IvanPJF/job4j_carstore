package ru.job4j.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.dto.FilterAdvert;
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
import java.util.*;

public class AdvertServlet extends HttpServlet {

    private final Service service = ValidateService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String filterJson = req.getParameter("filterAdvert");
        Collection<Advert> adverts = null;
        ObjectMapper mapper = new ObjectMapper();
        if ("getByAdvertiser".equals(action)) {
            Advertiser advertiser = (Advertiser) req.getSession().getAttribute("advertiser");
            adverts = service.findAdvertsByAdvertiser(advertiser);
        } else {
            FilterAdvert filterAdvert = mapper.readValue(filterJson, FilterAdvert.class);
            adverts = service.allActiveAdverts(filterAdvert);
        }
        try (final PrintWriter writer = resp.getWriter()) {
            mapper.writeValue(writer, adverts);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("changeAdvertsStatus".equals(action)) {
            if (service.changeAdvertsStatus(buildAdverts(req))) {
                return;
            }
            resp.sendError(500, "Statuses failed to change");
        }
    }

    private Map<Integer, Advert> buildAdverts(HttpServletRequest req) throws JsonProcessingException {
        Map<Integer, Advert> advertsMap = new HashMap<>();
        String advertsJson = req.getParameter("adverts");
        if (Objects.isNull(advertsJson)) {
            return advertsMap;
        }
        HttpSession session = req.getSession();
        Advertiser advertiser = (Advertiser) session.getAttribute("advertiser");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(advertsJson);
        Iterator<JsonNode> elements = rootNode.elements();
        while (elements.hasNext()) {
            Advert advert = mapper.readValue(elements.next().toString(), Advert.class);
            advert.setAdvertiser(advertiser);
            advertsMap.put(advert.getId(), advert);
        }
        return advertsMap;
    }
}
