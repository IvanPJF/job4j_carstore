package ru.job4j.persistence;

import ru.job4j.model.*;

import java.util.Collection;
import java.util.Map;

public interface Store {

    boolean addAdvert(Advert advert);
    boolean addAdvertiser(RegAdvertiser regAdvertiser);
    Collection<Advert> allActiveAdverts();
    Collection<Manufacturer> allManufacturers();
    Collection<Model> findModels(Manufacturer manufacturer);
    Collection<BodyType> findBodyTypes(Model model);
    Collection<Advert> findAdvertsByAdvertiser(Advertiser advertiser);
    Advertiser findAdvertiserByLogin(RegAdvertiser regAdvertiser);
    boolean isCredential(RegAdvertiser regAdvertiser);
    boolean changeAdvertsStatus(Map<Integer, Advert> adverts);
}
