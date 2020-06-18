package ru.job4j.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

public class HiberStore implements Store {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();
    private static final Store STORE = new HiberStore();

    private HiberStore() {
    }

    public static Store getInstance() {
        return STORE;
    }

    @Override
    public boolean addAdvert(Advert advert) {
        return Objects.nonNull(execute(session -> session.save(advert)));
    }

    @Override
    public boolean addAdvertiser(RegAdvertiser regAdvertiser) {
        return Objects.nonNull(execute(session -> {
            session.save(regAdvertiser.getAdvertiser());
            return session.save(regAdvertiser);
        }));
    }

    @Override
    public Collection<Advert> allActiveAdverts() {
        return execute(session -> session.createQuery("from Advert where status = true order by id", Advert.class).list());
    }

    @Override
    public Collection<Manufacturer> allManufacturers() {
        return execute(session -> session.createQuery("from Manufacturer order by name", Manufacturer.class).list());
    }

    @Override
    public Collection<Model> findModels(Manufacturer manufacturer) {
        return execute(session -> session.createQuery("from Model where manufacturer.id = :idManufacturer order by name", Model.class)
                .setParameter("idManufacturer", manufacturer.getId())
                .list());
    }

    @Override
    public Collection<BodyType> findBodyTypes(Model model) {
        return execute(session -> session.get(Model.class, model.getId()).getBodyTypes());
    }

    @Override
    public Collection<Advert> findAdvertsByAdvertiser(Advertiser advertiser) {
        return execute(session -> session.get(Advertiser.class, advertiser.getId()).getAdverts());
    }

    @Override
    public Advertiser findAdvertiserByLogin(RegAdvertiser regAdvertiser) {
        return execute(session -> session.createQuery("from RegAdvertiser where login = :login", RegAdvertiser.class)
                .setParameter("login", regAdvertiser.getLogin())
                .uniqueResult()
                .getAdvertiser());
    }

    @Override
    public boolean isCredential(RegAdvertiser regAdvertiser) {
        return Objects.nonNull(execute(session -> session.createQuery("from RegAdvertiser where login = :login and password = :password", RegAdvertiser.class)
                .setParameter("login", regAdvertiser.getLogin())
                .setParameter("password", regAdvertiser.getPassword())
                .uniqueResult())
        );
    }

    @Override
    public boolean changeAdvertsStatus(Collection<Advert> adverts) {
        return execute(session -> {
            session.get
        });
    }

    private <T> T execute(Function<Session, T> function) {
        try (final Session session = SESSION_FACTORY.openSession()) {
            try {
                session.beginTransaction();
                T result = function.apply(session);
                session.getTransaction().commit();
                return result;
            } catch (Exception e) {
                session.getTransaction().rollback();
                e.printStackTrace();
                return null;
            }
        }
    }

    private static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
        return null;
    }
}
