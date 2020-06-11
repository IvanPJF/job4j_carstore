package ru.job4j.model;

import javax.persistence.*;

@Entity
@Table(name = "advert")
public class Advert implements Comparable<Advert> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "vin", nullable = false, unique = true)
    private String vin;

    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(
            name = "model_id",
            nullable = false,
            updatable = false
    )
    private Model model;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(
            name = "body_type_id",
            nullable = false,
            updatable = false
    )
    private BodyType bodyType;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "advertiser_id", nullable = false, updatable = false)
    private Advertiser advertiser;

    @Override
    public int compareTo(Advert o) {
        return Integer.compare(this.getId(), o.getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(Advertiser advertiser) {
        this.advertiser = advertiser;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }
}
