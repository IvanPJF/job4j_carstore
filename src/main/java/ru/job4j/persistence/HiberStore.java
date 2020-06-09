package ru.job4j.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Advert;
import ru.job4j.model.Manufacturer;
import ru.job4j.model.Model;

import java.util.Collection;
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
    public void addAdvert(Advert advert) {
        execute(session -> session.save(advert));
    }

    @Override
    public Collection<Advert> allAdverts() {
        return execute(session -> session.createQuery("from Advert order by id", Advert.class).list());
    }

    @Override
    public Collection<Manufacturer> allManufacturers() {
        return execute(session -> session.createQuery("from Manufacturer order by name", Manufacturer.class).list());
    }

    @Override
    public Collection<Model> findModelsByIdManufacturer(Integer idManufacturer) {
        return execute(session -> session.createQuery("from Model where manufacturer.id = :idManufacturer order by name", Model.class)
                .setParameter("idManufacturer", idManufacturer)
                .list());
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
                throw e;
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