package ru.job4j.service;

import ru.job4j.model.*;

import java.util.Collection;

public interface Service {

    void addAdvert(Advert advert);
    boolean addAdvertiser(RegAdvertiser regAdvertiser);
    Collection<Advert> allAdverts();
    Collection<Manufacturer> allManufacturers();
    Collection<Model> findModels(Manufacturer manufacturer);
    Collection<BodyType> findBodyTypes(Model model);
    Advertiser findAdvertiserByLogin(RegAdvertiser regAdvertiser);
    boolean isCredential(RegAdvertiser regAdvertiser);
}
