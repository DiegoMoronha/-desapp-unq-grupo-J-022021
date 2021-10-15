package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class CriptoPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id  ;
    private LocalDateTime hourCotization;
    private String symbol;
    private String price ;
    private String priceArs;

    public void setPrice(String price) {
        this.price = price;
    }
    public void setHourCotization(LocalDateTime hourCotization) {
        this.hourCotization = hourCotization;
    }

    public String getSymbol() {
        return symbol;
    }
    public LocalDateTime getHourCotization() {
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
