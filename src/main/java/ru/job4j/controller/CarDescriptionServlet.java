package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.model.Manufacturer;
import ru.job4j.model.Model;
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
        String action = req.getParameter("action");
        String manufacturerJSON = req.getParameter("manufacturer");
        String modelJSON = req.getParameter("model");
        Manufacturer manufacturer = null;
        Model model = null;
        ObjectMapper mapper = new ObjectMapper();
        if (Objects.nonNull(manufacturerJSON)) {
            manufacturer = mapper.readValue(manufacturerJSON, Manufacturer.class);
        }
        if (Objects.nonNull(modelJSON)) {
            model = mapper.readValue(modelJSON, Model.class);
        }
        Object result = null;
        if ("getManufacturers".equals(action)) {
            result = SERVICE.allManufacturers();
        } else if ("getModels".equals(action)) {
            result = SERVICE.findModels(manufacturer);
        } else if ("getBodyTypes".equals(action)) {
            result = SERVICE.findBodyTypes(model);
        }
        try (final PrintWriter writer = resp.getWriter()) {
            mapper.writeValue(writer, result);
        }

    }
}
