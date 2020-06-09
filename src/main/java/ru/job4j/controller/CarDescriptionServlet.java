package ru.job4j.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.dto.CarDescription;
import ru.job4j.model.Manufacturer;
import ru.job4j.model.Model;
import ru.job4j.service.Service;
import ru.job4j.service.ValidateService;
import ru.job4j.util.DispatcherCar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class CarDescriptionServlet extends HttpServlet {

    private static final Service SERVICE = ValidateService.getInstance();
    private static final DispatcherCar DISPATCH_CAR = DispatcherCar.getInstance();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        DISPATCH_CAR.load("getManufacturers", (carDesc, service) -> SERVICE.allManufacturers());
        DISPATCH_CAR.load("getModels", (carDesc, service) -> SERVICE.findModels(carDesc));
        DISPATCH_CAR.load("getBodyTypes", (carDesc, service) -> SERVICE.findBodyTypes(carDesc));
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String action = req.getParameter("action");
        Manufacturer manufacturer = parseJson(req.getParameter("manufacturer"), Manufacturer.class);
        Model model = parseJson(req.getParameter("model"), Model.class);
        CarDescription carDesc = new CarDescription(manufacturer, model);
        Object collection = DISPATCH_CAR.execute(action, carDesc, SERVICE);
        try (final PrintWriter writer = resp.getWriter()) {
            MAPPER.writeValue(writer, collection);
        }
    }

    private <T> T parseJson(String json, Class<T> clazz) throws JsonProcessingException {
        if (Objects.isNull(json)) {
            return null;
        }
        return MAPPER.readValue(json, clazz);
    }
}
