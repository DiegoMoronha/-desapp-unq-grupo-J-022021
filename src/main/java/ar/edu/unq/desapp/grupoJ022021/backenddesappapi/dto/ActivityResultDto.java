package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto;

public class ActivityResultDto {
    private String hour;
    private String criptoName;
    private String valueCripto;
    private String amountInArs;
    private String username;
    private String lastName;
    private Integer operations;
    private Long reputation;

    public ActivityResultDto(String hour, String criptoName, String valueCripto, String amountInArs, String username
            , String lastName, Integer operations, Long reputation) {
        this.hour = hour;
        this.criptoName = criptoName;
        this.valueCripto = valueCripto;
        this.amountInArs = amountInArs;
        this.username = username;
        this.lastName = lastName;
        this.operations = operations;
        this.reputation = reputation;
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

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getOperations() {
        return operations;
    }

    public Long getReputation() {
        return reputation;
    }
}
