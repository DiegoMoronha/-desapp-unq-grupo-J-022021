package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class CriptoPrice {


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
