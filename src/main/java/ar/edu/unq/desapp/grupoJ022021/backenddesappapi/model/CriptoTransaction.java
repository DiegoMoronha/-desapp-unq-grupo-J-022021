package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CriptoTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private LocalDateTime hour;
    private String transactionType;
    private String criptoName;
    private Long nominals;
    private String criptoAmount ;
    private String amountInArs;
    private Long  score;

    @ManyToOne
    private User user;

    public CriptoTransaction(){}

    public CriptoTransaction(LocalDateTime hour, String transactionType, String criptoName, Long nominals,
                             String criptoAmount, String amountInArs, Long score, User user) {
        this.hour = hour;
        this.transactionType = transactionType;
        this.criptoName = criptoName;
        this.nominals = nominals;
        this.criptoAmount = criptoAmount;
        this.amountInArs = amountInArs;
        this.score = score;
        this.user = user;
    }
}
