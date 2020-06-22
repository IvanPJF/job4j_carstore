package ru.job4j.util;

import ru.job4j.dto.CarDescription;
import ru.job4j.service.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;

public class DispatcherCar {

    private final Map<String, BiFunction<CarDescription, Service, Object>> actionsMap =
            new HashMap<>();
    private static final DispatcherCar DISPATCH = new DispatcherCar();

    private DispatcherCar() {
    }

    public static DispatcherCar getInstance() {
        return DISPATCH;
    }

    public void load(String action, BiFunction<CarDescription, Service, Object> func) {
        actionsMap.put(action, func);
    }

    public Object execute(String action, CarDescription carDescription, Service service) {
        BiFunction<CarDescription, Service, Object> func = actionsMap.get(action);
        if (Objects.isNull(func)) {
            throw new NoSuchElementException("No action found.");
        }
        return func.apply(carDescription, service);
    }


}
