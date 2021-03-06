package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.model.Advertiser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class AdvertiserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Advertiser advertiser = (Advertiser) req.getSession().getAttribute("advertiser");
        if (Objects.isNull(advertiser)) {
            return;
        }
        try (final PrintWriter writer = resp.getWriter()) {
            new ObjectMapper().writeValue(writer, advertiser);
        }
    }
}
