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

    private final Service service = ValidateService.getInstance();
    private final DispatcherCar dispatchCar = DispatcherCar.getInstance();

    @Override
    public void init() throws ServletException {
        dispatchCar.load("getManufacturers", (carDesc, service) -> service.allManufacturers());
        dispatchCar.load("getModels", (carDesc, service) -> service.findModels(carDesc));
        dispatchCar.load("getBodyTypes", (carDesc, service) -> service.findBodyTypes(carDesc));
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        ObjectMapper mapper = new ObjectMapper();
        Manufacturer manufacturer = parseJson(
                req.getParameter("manufacturer"), Manufacturer.class, mapper
        );
        Model model = parseJson(req.getParameter("model"), Model.class, mapper);
        CarDescription carDesc = new CarDescription(manufacturer, model);
        Object collection = dispatchCar.execute(action, carDesc, service);
        try (final PrintWriter writer = resp.getWriter()) {
            mapper.writeValue(writer, collection);
        }
    }

    private <T> T parseJson(String json, Class<T> clazz, ObjectMapper mapper) throws JsonProcessingException {
        if (Objects.isNull(json)) {
            return null;
        }
        return mapper.readValue(json, clazz);
    }
}
