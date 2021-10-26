package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto;

import java.time.LocalDateTime;

public class UserTransactionDto {

    private LocalDateTime hour;
    private String username;
    private String lastName;
    private String criptoName;
    private String cotization;
    private String amountInArs;
    private Long nominals;
    private Integer operations;
    private Long reputation;
    private String addrOrCvu;

    public UserTransactionDto(LocalDateTime hour,String username, String lastName, String criptoName,
                              String valueCripto, String amountInArs, Long nominals, Integer operations,
                              Long reputation, String addrOrCvu) {
        this.hour=hour;
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

    public LocalDateTime getHour(){return hour;}
}
