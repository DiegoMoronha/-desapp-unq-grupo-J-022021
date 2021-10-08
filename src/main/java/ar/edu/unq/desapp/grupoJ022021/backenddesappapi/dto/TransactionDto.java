package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto;

public class TransactionDto {
    private Long idToNegociate ;
    private String transactionType;
    private String addrToSend;
    private String criptoName;
    private String  criptoAmount;
    private String amountInArs;

    public TransactionDto(Long idToNegociate, String transactionType, String addrToSend,
                          String criptoName, String criptoAmount, String amountInArs) {
        this.idToNegociate = idToNegociate;
        this.transactionType = transactionType;
        this.addrToSend = addrToSend;
        this.criptoName = criptoName;
        this.criptoAmount = criptoAmount;
        this.amountInArs = amountInArs;
    }

    public Long getIdToNegociate() {
        return idToNegociate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getAddrToSend() {
        return addrToSend;
    }

    public String getCriptoName() {
        return criptoName;
    }

    public String getCriptoAmount() {
        return criptoAmount;
    }

    public String getAmountInArs() {
        return amountInArs;
    }
}
