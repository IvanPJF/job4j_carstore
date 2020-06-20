package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.model.RegAdvertiser;
import ru.job4j.service.Service;
import ru.job4j.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddAdvertiserServlet extends HttpServlet {

    private static final Service SERVICE = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/html/addAdvertiser.html").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        RegAdvertiser regAdvertiser = mapper.readValue(req.getParameter("regAdvertiser"), RegAdvertiser.class);
        if (SERVICE.addAdvertiser(regAdvertiser)) {
            return;
        }
        resp.sendError(500, "Failed to add advertiser");
    }
}
