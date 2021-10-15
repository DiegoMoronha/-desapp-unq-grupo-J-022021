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
    private String criptoAmount ;
    private String amountInArs;
    private Long  score;

    @ManyToOne
    private User user;

    public CriptoTransaction(LocalDateTime hour, String transactionType, String criptoName, String criptoAmount,
                             String amount, Long score)
    {
        this.hour = hour;
        this.transactionType = transactionType;
        this.criptoName = criptoName;
        this.criptoAmount = criptoAmount;
        this.amountInArs = amount;
        this.score = score;
    }


}
