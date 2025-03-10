package co.edu.escuelaing.edu.arep.secure.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Property" )
public class Property {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String address;
    private Double price;
    private Double size;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
