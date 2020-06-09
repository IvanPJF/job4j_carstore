package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.service.Service;
import ru.job4j.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class CarDescriptionServlet extends HttpServlet {

    private static final Service SERVICE = ValidateService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        String action = req.getParameter("action");
        String idManufacturerString = req.getParameter("idManufacturer");
        Integer idManufacturer = Objects.nonNull(idManufacturerString) ? Integer.parseInt(idManufacturerString) : null;
        Object result = null;
        if ("getManufacturers".equals(action)) {
            result = SERVICE.allManufacturers();
        } else if ("getModels".equals(action)) {
            result = SERVICE.findModelsByIdManufacturer(idManufacturer);
        }
        try (final PrintWriter writer = resp.getWriter()) {
            mapper.writeValue(writer, result);
        }

    }
}
