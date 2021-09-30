package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
public class CriptoPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private final String updateAt= dateHour();
    private String symbol;
    private String price ;
    private String priceArs;

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPrice() {
        return price;
    }

    public String dateHour() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime today = LocalTime.now();
        String timeString = today.format(formatter);
        return timeString;
    }

    public Long getId() {
        return id;
    }
    public void setPriceArs(String priceArs) {
        this.priceArs = priceArs;
    }

    public String getPriceArs() {
        return priceArs;
    }
    public String getUpdateAt() {
        return updateAt;
    }
}
