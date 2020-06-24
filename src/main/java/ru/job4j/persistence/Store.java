package ru.job4j.persistence;

import ru.job4j.dto.CarDescription;
import ru.job4j.dto.FilterAdvert;
import ru.job4j.model.*;

import java.util.Collection;
import java.util.Map;

public interface Store {

    boolean addAdvert(Advert advert);
    boolean addAdvertiser(RegAdvertiser regAdvertiser);
    Collection<Advert> allActiveAdverts(FilterAdvert filterAdvert);
    Collection<Manufacturer> allManufacturers();
    Collection<Model> findModels(CarDescription carDescription);
    Collection<BodyType> findBodyTypes(CarDescription carDescription);
    Collection<Advert> findAdvertsByAdvertiser(Advertiser advertiser);
    Advertiser findAdvertiserByLogin(RegAdvertiser regAdvertiser);
    boolean isCredential(RegAdvertiser regAdvertiser);
    boolean changeAdvertsStatus(Map<Integer, Advert> adverts);
}
