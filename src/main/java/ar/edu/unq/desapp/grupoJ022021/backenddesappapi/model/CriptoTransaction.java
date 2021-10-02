package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;

import javax.persistence.*;

@Entity
public class CriptoTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String  hour;
    private String transactionType;
    private String criptoName;
    private Long criptoAmount ;
    private Long amountInArs;
    private String  pointUserReceiver;
    private String  addressToSendPay;

    @ManyToOne
    private User user;

    public CriptoTransaction(String hour, String transactionType, String criptoName, Long criptoAmount,
                             Long amount, String pointUserReceiver, String addressToSendPay)
    {
        this.hour = hour;
        this.transactionType = transactionType;
        this.criptoName = criptoName;
        this.criptoAmount = criptoAmount;
        this.amountInArs = amount;
        this.pointUserReceiver = pointUserReceiver;
        this.addressToSendPay = addressToSendPay;
    }


}
