package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto;

public class ActivityDto {
    private String criptoName;
    private String valueCripto;
    private Long nominals;
    private String amountInArs;
    private String activityType;

    public ActivityDto(String criptoName, String valueCripto,
                       Long nominals, String amountInArs, String activityType) {
        this.criptoName = criptoName;
        this.valueCripto = valueCripto;
        this.nominals = nominals;
        this.amountInArs = amountInArs;
        this.activityType = activityType;
    }

    public Long getNominals() {
        return nominals;
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

    public String getActivityType() {
        return activityType;
    }

}
