package ru.job4j.dto;

import ru.job4j.model.Manufacturer;
import ru.job4j.model.Model;

public class CarDescription {

    private Manufacturer manufacturer;

    private Model model;

    public CarDescription(Manufacturer manufacturer, Model model) {
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
