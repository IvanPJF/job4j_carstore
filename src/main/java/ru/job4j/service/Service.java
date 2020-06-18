package ru.job4j.service;

import ru.job4j.model.*;

import java.util.Collection;

public interface Service {

    boolean addAdvert(Advert advert);
    boolean addAdvertiser(RegAdvertiser regAdvertiser);
    Collection<Advert> allActiveAdverts();
    Collection<Manufacturer> allManufacturers();
    Collection<Model> findModels(Manufacturer manufacturer);
    Collection<BodyType> findBodyTypes(Model model);
    Collection<Advert> findAdvertsByAdvertiser(Advertiser advertiser);
    Advertiser findAdvertiserByLogin(RegAdvertiser regAdvertiser);
    boolean isCredential(RegAdvertiser regAdvertiser);
    boolean changeAdvertsStatus(Collection<Advert> adverts);
}
