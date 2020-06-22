package ru.job4j.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static ru.job4j.util.LexicalOrder.compare;

@Entity
@Table(name = "model")
public class Model implements Comparable<Model> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "manufacturer_id",
            nullable = false,
            updatable = false
    )
    private Manufacturer manufacturer;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "model_body_type",
            joinColumns = @JoinColumn(name = "model_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(
                    name = "body_type_id", nullable = false, updatable = false
            )
    )
    private Set<BodyType> bodyTypes = new TreeSet<>();

    @Override
    public int compareTo(Model o) {
        return compare(this.name, o.getName());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Set<BodyType> getBodyTypes() {
        return bodyTypes;
    }

    public void setBodyTypes(Set<BodyType> bodyTypes) {
        this.bodyTypes = bodyTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Model model = (Model) o;
        return Objects.equals(id, model.id)
                && Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
