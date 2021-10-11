package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto;

public class TransactionDto {
    private String transactionType;
    private String criptoName;
    private String  criptoAmount;
    private String amountInArs;

    public TransactionDto(String transactionType, String addrToSend,
                          String criptoName, String criptoAmount, String amountInArs) {
        this.transactionType = transactionType;
        this.criptoName = criptoName;
        this.criptoAmount = criptoAmount;
        this.amountInArs = amountInArs;
    }

    public String getTransactionType() {
        return transactionType;
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
