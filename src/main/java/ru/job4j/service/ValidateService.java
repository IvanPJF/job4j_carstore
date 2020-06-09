package ru.job4j.service;

import ru.job4j.model.Advert;
import ru.job4j.model.Manufacturer;
import ru.job4j.model.Model;
import ru.job4j.persistence.HiberStore;
import ru.job4j.persistence.Store;

import java.util.Collection;

public class ValidateService implements Service {

    private static final Store STORE = HiberStore.getInstance();
    private static final Service SERVICE = new ValidateService();

    private ValidateService() {
    }

    public static Service getInstance() {
        return SERVICE;
    }

    @Override
    public void addAdvert(Advert advert) {
        STORE.addAdvert(advert);
    }

    @Override
    public Collection<Advert> allAdverts() {
        return STORE.allAdverts();
    }

    @Override
    public Collection<Manufacturer> allManufacturers() {
        return STORE.allManufacturers();
    }

    @Override
    public Collection<Model> findModelsByIdManufacturer(Integer idManufacturer) {
        return STORE.findModelsByIdManufacturer(idManufacturer);
    }
}
