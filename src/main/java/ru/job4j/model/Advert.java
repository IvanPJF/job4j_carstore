package ru.job4j.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "advert")
@FilterDefs({
        @FilterDef(name = "lastDay", defaultCondition = "create_date >= 'today'"),
        @FilterDef(name = "withPhoto", defaultCondition = "photo_name is not null"),
        @FilterDef(
                name = "byCarManufacturer",
                parameters = @ParamDef(name = "manufacturer_id", type = "int")
        )
})
@Filters({
        @Filter(name = "lastDay"),
        @Filter(name = "withPhoto"),
        @Filter(name = "byCarManufacturer", condition = "manufacturer_id = :manufacturer_id")
})
public class Advert implements Comparable<Advert> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "vin", nullable = false, unique = true)
    private String vin;

    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Column(name = "photo_name", unique = true)
    private String photoName;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "status", insertable = false)
    @ColumnDefault(value = "true")
    private Boolean status;

    @Column(name = "create_date", insertable = false)
    @ColumnDefault(value = "current_timestamp")
    private Date createDate;

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

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(
            name = "manufacturer_id",
            nullable = false,
            updatable = false
    )
    private Manufacturer manufacturer;

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

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

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Advert advert = (Advert) o;
        return Objects.equals(id, advert.id)
                && Objects.equals(vin, advert.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vin);
    }
}
