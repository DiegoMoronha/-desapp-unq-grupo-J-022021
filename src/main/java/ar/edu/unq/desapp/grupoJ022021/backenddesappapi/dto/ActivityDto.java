package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto;

public class ActivityDto {
    private String criptoName;
    private Double valueCripto;
    private Long nominals;
    private Double amountInArs;
    private String activityType;

    public ActivityDto(String criptoName, Double valueCripto,
                       Long nominals, Double amountInArs, String activityType) {
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

    public Double getValueCripto() {
        return valueCripto;
    }

    public Double getAmountInArs() {
        return amountInArs;
    }

    public String getActivityType() {
        return activityType;
    }

}
