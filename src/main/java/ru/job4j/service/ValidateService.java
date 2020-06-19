package ru.job4j.service;

import ru.job4j.model.*;
import ru.job4j.persistence.HiberStore;
import ru.job4j.persistence.Store;

import java.util.Collection;
import java.util.Map;

public class ValidateService implements Service {

    private static final Store STORE = HiberStore.getInstance();
    private static final Service SERVICE = new ValidateService();

    private ValidateService() {
    }

    public static Service getInstance() {
        return SERVICE;
    }

    @Override
    public boolean addAdvert(Advert advert) {
        return STORE.addAdvert(advert);
    }

    @Override
    public boolean addAdvertiser(RegAdvertiser regAdvertiser) {
        return STORE.addAdvertiser(regAdvertiser);
    }

    @Override
    public Collection<Advert> allActiveAdverts() {
        return STORE.allActiveAdverts();
    }

    @Override
    public Collection<Manufacturer> allManufacturers() {
        return STORE.allManufacturers();
    }

    @Override
    public Collection<Model> findModels(Manufacturer manufacturer) {
        return STORE.findModels(manufacturer);
    }

    @Override
    public Collection<BodyType> findBodyTypes(Model model) {
        return STORE.findBodyTypes(model);
    }

    @Override
    public Collection<Advert> findAdvertsByAdvertiser(Advertiser advertiser) {
        return STORE.findAdvertsByAdvertiser(advertiser);
    }

    @Override
    public Advertiser findAdvertiserByLogin(RegAdvertiser regAdvertiser) {
        return STORE.findAdvertiserByLogin(regAdvertiser);
    }

    @Override
    public boolean isCredential(RegAdvertiser regAdvertiser) {
        return STORE.isCredential(regAdvertiser);
    }

    @Override
    public boolean changeAdvertsStatus(Map<Integer, Advert> adverts) {
        return STORE.changeAdvertsStatus(adverts);
    }
}
