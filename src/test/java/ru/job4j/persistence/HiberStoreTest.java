package ru.job4j.persistence;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dto.CarDescription;
import ru.job4j.dto.FilterAdvert;
import ru.job4j.model.*;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HiberStoreTest {

    private static final Store STORE = HiberStore.getInstance();
    private Advertiser advertiser;
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private static Manufacturer defaultManufacturer;
    private static Model defaultModel;
    private static BodyType defaultBodyType;

    @BeforeClass
    public static void initCarDescription() {
        defaultManufacturer = new Manufacturer();
        defaultManufacturer.setId(1);
        defaultModel = new Model();
        defaultModel.setId(1);
        defaultBodyType = new BodyType();
        defaultBodyType.setId(1);
    }

    @Before
    public void createAdvertiser() {
        Advertiser advr = new Advertiser();
        advr.setName("name");
        advr.setPhone(100L);
        RegAdvertiser regAdvertiser = new RegAdvertiser();
        regAdvertiser.setLogin("login" + COUNTER.getAndIncrement());
        regAdvertiser.setPassword("password");
        regAdvertiser.setAdvertiser(advr);
        if (!STORE.addAdvertiser(regAdvertiser)) {
            throw new RuntimeException("Not created test Advertiser");
        }
        this.advertiser = advr;
    }

    @Test
    public void whenAddAdvertThenAdvert() {
        Advert advert = new Advert();
        advert.setVin("vinTestAddAdvert");
        advert.setMileage(0);
        advert.setPrice(1000L);
        advert.setAdvertiser(advertiser);
        advert.setManufacturer(defaultManufacturer);
        advert.setModel(defaultModel);
        advert.setBodyType(defaultBodyType);
        boolean result = STORE.addAdvert(advert);
        Advert resultAdvert = STORE.findAdvertsByAdvertiser(advertiser).iterator().next();
        assertThat(result, is(true));
        assertThat(resultAdvert, is(advert));
    }

    @Test
    public void whenAddAdvertiser() {
        Advertiser advertiser = new Advertiser();
        advertiser.setName("name");
        advertiser.setPhone(8800L);
        RegAdvertiser regAdvertiser = new RegAdvertiser();
        regAdvertiser.setLogin("login");
        regAdvertiser.setPassword("password");
        regAdvertiser.setAdvertiser(advertiser);
        boolean result = STORE.addAdvertiser(regAdvertiser);
        Advertiser resultAdvertiser = STORE.findAdvertiserByLogin(regAdvertiser);
        assertThat(result, is(true));
        assertThat(resultAdvertiser, is(advertiser));
    }

    @Test
    public void whenChangeStatusAdvertThenTrue() {
        Advert advert = new Advert();
        advert.setVin("vinTestChangeStatus");
        advert.setMileage(0);
        advert.setPrice(1000L);
        advert.setAdvertiser(advertiser);
        advert.setManufacturer(defaultManufacturer);
        advert.setModel(defaultModel);
        advert.setBodyType(defaultBodyType);
        STORE.addAdvert(advert);
        advert.setStatus(false);
        boolean result = STORE.changeAdvertsStatus(Map.of(advert.getId(), advert));
        assertThat(result, is(true));
    }

    @Test
    public void whenAllManufacturersThenThreeManufacturers() {
        Collection<Manufacturer> manufacturers = STORE.allManufacturers();
        assertThat(manufacturers.size(), is(3));
    }

    @Test
    public void whenFindModelsThenOneModels() {
        Collection<Model> models = STORE.findModels(new CarDescription(defaultManufacturer, null));
        assertThat(models.size(), is(1));
    }

    @Test
    public void whenFindBodyTypeThenTwoBodyType() {
        Collection<BodyType> bodyTypes = STORE.findBodyTypes(
                new CarDescription(null, defaultModel)
        );
        assertThat(bodyTypes.size(), is(2));
    }

    @Test
    public void whenAdvertiserIsCredentialThenTrue() {
        Advertiser advertiser = new Advertiser();
        advertiser.setName("name");
        advertiser.setPhone(8800L);
        RegAdvertiser regAdvertiser = new RegAdvertiser();
        regAdvertiser.setLogin("lgTrue");
        regAdvertiser.setPassword("password");
        regAdvertiser.setAdvertiser(advertiser);
        STORE.addAdvertiser(regAdvertiser);
        boolean result = STORE.isCredential(regAdvertiser);
        assertThat(result, is(true));
    }

    @Test
    public void whenAdvertiserNotIsCredentialThenFalse() {
        Advertiser advertiser = new Advertiser();
        advertiser.setName("name");
        advertiser.setPhone(8800L);
        RegAdvertiser regAdvertiser = new RegAdvertiser();
        regAdvertiser.setLogin("lgFalse");
        regAdvertiser.setPassword("password");
        regAdvertiser.setAdvertiser(advertiser);
        STORE.addAdvertiser(regAdvertiser);
        regAdvertiser.setPassword("not valid password");
        boolean result = STORE.isCredential(regAdvertiser);
        assertThat(result, is(false));
    }

    @Test
    public void whenAllActiveAdvertsThen() {
        Advert advert = new Advert();
        advert.setVin("vinTestAllActiveAdverts");
        advert.setMileage(0);
        advert.setPrice(1000L);
        advert.setAdvertiser(advertiser);
        advert.setManufacturer(defaultManufacturer);
        advert.setModel(defaultModel);
        advert.setBodyType(defaultBodyType);
        STORE.addAdvert(advert);
        advert.setStatus(false);
        Collection<Advert> result = STORE.allActiveAdverts(new FilterAdvert());
        assertThat(result.size(), greaterThanOrEqualTo(1));
    }
}