package ru.job4j.persistence;

import ru.job4j.model.*;

import java.util.Collection;

public interface Store {

    void addAdvert(Advert advert);
    Collection<Advert> allAdverts();
    Collection<Manufacturer> allManufacturers();
    Collection<Model> findModels(Manufacturer manufacturer);
    Collection<BodyType> findBodyTypes(Model model);
    boolean isCredential(RegAdvertiser regAdvertiser);
    Advertiser findAdvertiserByLogin(RegAdvertiser regAdvertiser);
}
