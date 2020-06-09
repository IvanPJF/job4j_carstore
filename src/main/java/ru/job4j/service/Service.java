package ru.job4j.service;

import ru.job4j.model.Advert;
import ru.job4j.model.Manufacturer;
import ru.job4j.model.Model;

import java.util.Collection;

public interface Service {

    void addAdvert(Advert advert);
    Collection<Advert> allAdverts();
    Collection<Manufacturer> allManufacturers();
    Collection<Model> findModelsByIdManufacturer(Integer idManufacturer);
}
