package ru.job4j.service;

import ru.job4j.dto.CarDescription;
import ru.job4j.model.*;
import ru.job4j.persistence.HiberStore;
import ru.job4j.persistence.Store;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ValidateService implements Service {

    private final Store store = HiberStore.getInstance();
    private static final Service SERVICE = new ValidateService();

    private ValidateService() {
    }

    public static Service getInstance() {
        return SERVICE;
    }

    @Override
    public boolean addAdvert(Advert advert) {
        if (Objects.isNull(advert)) {
            return false;
        }
        return store.addAdvert(advert);
    }

    @Override
    public boolean addAdvertiser(RegAdvertiser regAdvertiser) {
        return store.addAdvertiser(regAdvertiser);
    }

    @Override
    public Collection<Advert> allActiveAdverts() {
        return store.allActiveAdverts();
    }

    @Override
    public Collection<Manufacturer> allManufacturers() {
        return store.allManufacturers();
    }

    @Override
    public Collection<Model> findModels(CarDescription carDescription) {
        if (Objects.isNull(carDescription.getManufacturer())) {
            return List.of();
        }
        return store.findModels(carDescription);
    }

    @Override
    public Collection<BodyType> findBodyTypes(CarDescription carDescription) {
        if (Objects.isNull(carDescription.getModel())) {
            return List.of();
        }
        return store.findBodyTypes(carDescription);
    }

    @Override
    public Collection<Advert> findAdvertsByAdvertiser(Advertiser advertiser) {
        return store.findAdvertsByAdvertiser(advertiser);
    }

    @Override
    public Advertiser findAdvertiserByLogin(RegAdvertiser regAdvertiser) {
        return store.findAdvertiserByLogin(regAdvertiser);
    }

    @Override
    public boolean isCredential(RegAdvertiser regAdvertiser) {
        return store.isCredential(regAdvertiser);
    }

    @Override
    public boolean changeAdvertsStatus(Map<Integer, Advert> adverts) {
        if (adverts.isEmpty()) {
            return false;
        }
        return store.changeAdvertsStatus(adverts);
    }
}
