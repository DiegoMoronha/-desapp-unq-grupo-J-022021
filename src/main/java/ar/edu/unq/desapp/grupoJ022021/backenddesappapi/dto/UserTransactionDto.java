package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto;

public class UserTransactionDto {
    private String username;
    private String lastName;
    private String criptoName;
    private String cotization;
    private String amountInArs;
    private Long nominals;
    private Integer operations;
    private Long reputation;
    private String addrOrCvu;

    public UserTransactionDto(String username, String lastName, String criptoName,
                              String valueCripto, String amountInArs, Long nominals, Integer operations,
                              Long reputation, String addrOrCvu) {
        this.username = username;
        this.lastName = lastName;
        this.criptoName = criptoName;
        this.cotization = valueCripto;
        this.amountInArs = amountInArs;
        this.nominals = nominals;
        this.operations = operations;
        this.reputation = reputation;
        this.addrOrCvu = addrOrCvu;
    }

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCriptoName() {
        return criptoName;
    }

    public String getCotization() {
        return cotization;
    }

    public String getAmountInArs() {
        return amountInArs;
    }

    public Integer getOperations() {
        return operations;
    }

    public Long getReputation() {
        return reputation;
    }

    public String getAddrOrCvu() {
        return addrOrCvu;
    }

    public Long getNominals() {
        return nominals;
    }
}
