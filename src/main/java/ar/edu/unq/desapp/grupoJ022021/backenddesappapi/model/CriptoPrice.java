package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CriptoPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id  ;
    private String hourCotization;
    private String symbol;
    private String price ;
    private String priceArs;

    public void setPrice(String price) {
        this.price = price;
    }
    public void setHourCotization(String hourCotization) {
        this.hourCotization = hourCotization;
    }

    public String getSymbol() {
        return symbol;
    }
    public String getHourCotization() {
        return hourCotization;
    }
    public String getPriceUsd() {
        return price;
    }


    public void setPriceArs(String priceArs) {
        this.priceArs = priceArs;
    }
    public String getPriceArs() {
        return priceArs;
    }

}
