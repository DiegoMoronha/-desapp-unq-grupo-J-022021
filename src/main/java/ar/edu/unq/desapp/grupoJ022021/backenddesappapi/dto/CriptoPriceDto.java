package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto;

import java.time.LocalDateTime;

public class CriptoPriceDto {

    private Long id  ;
    private LocalDateTime hourCotization;
    private String symbol;
    private Double price ;
    private Double priceArs;

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


    public void setPriceArs(Double priceArs) {
        this.priceArs = priceArs;
    }
    public Double getPriceArs() {
        return priceArs;
    }

}
