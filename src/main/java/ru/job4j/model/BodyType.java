package ru.job4j.model;

import javax.persistence.*;

import static ru.job4j.util.LexicalOrder.compare;

@Entity
@Table(name = "body_type")
public class BodyType implements Comparable<BodyType> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Override
    public int compareTo(BodyType o) {
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
}
