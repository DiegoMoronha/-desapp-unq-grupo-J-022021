package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;

import javax.persistence.*;


@Entity
public class CriptoActivity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String hour;
   private String criptoName;
   private String valueCripto;
   private String amountInArs;
   private String activityType;



    @ManyToOne
   private User user;

   public CriptoActivity(){}

    public CriptoActivity(String hour, String criptoName, String valueCripto, String amountInArs,
                          String activityType, User user) {
        this.hour = hour;
        this.criptoName = criptoName;
        this.valueCripto = valueCripto;
        this.amountInArs = amountInArs;
        this.user = user;
        this.activityType=activityType;
    }

    public Long getId() {
        return id;
    }

    public String getHour() {
        return hour;
    }

    public String getCriptoName() {
        return criptoName;
    }

    public String getValueCripto() {
        return valueCripto;
    }

    public String getAmountInArs() {
        return amountInArs;
    }

    public Long getUserReputation(){
       return user.getReputation();
    }
    public String getActivityType() {
        return activityType;
    }

    public User getUsername() {
        return user;
    }


}


