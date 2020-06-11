package ru.job4j.model;

import javax.persistence.*;

@Entity
@Table(name = "reg_advertiser")
public class RegAdvertiser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "advertiser_id", nullable = false, unique = true)
    private Advertiser advertiser;

    public RegAdvertiser() {
    }

    public RegAdvertiser(String login) {
        this(login, "");
    }

    public RegAdvertiser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(Advertiser advertiser) {
        this.advertiser = advertiser;
    }
}
