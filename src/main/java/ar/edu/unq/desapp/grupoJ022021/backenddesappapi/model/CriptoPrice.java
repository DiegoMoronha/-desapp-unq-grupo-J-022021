package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;

import java.time.LocalDateTime;

public class CriptoPrice {

    private Long id  ;
    private LocalDateTime hourCotization;
    private String symbol;
    private Double price ;
    private String priceArs;

    public void setPrice(Double price) {
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
    public Double getPriceUsd() {
        return price;
    }


    public void setPriceArs(String priceArs) {
        this.priceArs = priceArs;
    }
    public String getPriceArs() {
        return priceArs;
    }

}
