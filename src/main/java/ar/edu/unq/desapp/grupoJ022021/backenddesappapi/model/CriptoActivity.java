package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class CriptoActivity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private LocalDateTime hour;
   private String criptoName;
   private Long nominals;
   private Double valueCripto;
   private Double amountInArs;
   private String activityType;



    @ManyToOne
   private User user;

   public CriptoActivity(){}

    public CriptoActivity(LocalDateTime hour, String criptoName, Long nominals,
                          Double valueCripto, Double amountInArs, String activityType,
                          User user) {
        this.hour = hour;
        this.criptoName = criptoName;
        this.nominals = nominals;
        this.valueCripto = valueCripto;
        this.amountInArs = amountInArs;
        this.activityType = activityType;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public String getCriptoName() {
        return criptoName;
    }

    public Double getValueCripto() {
        return valueCripto;
    }

    public Double getAmountInArs() {
        return amountInArs;
    }

    public Long getUserReputation(){
       return user.getReputation();
    }
    public String getActivityType() {
        return activityType;
    }

    public User getUser() {
        return user;
    }


    public Long getNominals() {
        return nominals;
    }


}


