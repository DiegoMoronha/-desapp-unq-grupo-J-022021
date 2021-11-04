package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CriptoTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime hour;
    private String transactionType;
    private String criptoName;
    private Long nominals;
    private Double criptoAmount ;
    private Double amountInArs;
    private Long  score;

    @ManyToOne
    private User user;

    public CriptoTransaction(){}

    public CriptoTransaction(LocalDateTime hour, String transactionType, String criptoName, Long nominals,
                             Double criptoAmount, Double amountInArs, Long score, User user) {
        this.hour = hour;
        this.transactionType = transactionType;
        this.criptoName = criptoName;
        this.nominals = nominals;
        this.criptoAmount = criptoAmount;
        this.amountInArs = amountInArs;
        this.score = score;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getHour() {
        return hour;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getCriptoName() {
        return criptoName;
    }

    public Long getNominals() {
        return nominals;
    }

    public Double getCriptoAmount() {
        return criptoAmount;
    }

    public Double getAmountInArs() {
        return amountInArs;
    }

    public Long getScore() {
        return score;
    }

    public User getUser() {
        return user;
    }
}
