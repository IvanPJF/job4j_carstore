package ru.job4j.service;

import ru.job4j.dto.CarDescription;
import ru.job4j.model.*;

import java.util.Collection;
import java.util.Map;

public interface Service {

    boolean addAdvert(Advert advert);
    boolean addAdvertiser(RegAdvertiser regAdvertiser);
    Collection<Advert> allActiveAdverts();
    Collection<Manufacturer> allManufacturers();
    Collection<Model> findModels(CarDescription carDescription);
    Collection<BodyType> findBodyTypes(CarDescription carDescription);
    Collection<Advert> findAdvertsByAdvertiser(Advertiser advertiser);
    Advertiser findAdvertiserByLogin(RegAdvertiser regAdvertiser);
    boolean isCredential(RegAdvertiser regAdvertiser);
    boolean changeAdvertsStatus(Map<Integer, Advert> adverts);
}
