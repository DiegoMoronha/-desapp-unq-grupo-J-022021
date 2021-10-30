package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto;

import java.time.LocalDateTime;

public class ActivityResultDto {
    private LocalDateTime hour;
    private Long activityId;
    private Long userId;
    private String criptoName;
    private Double valueCripto;
    private Long nominals;
    private Double amountInArs;
    private String username;
    private String lastName;
    private Integer operations;
    private Long reputation;

    public ActivityResultDto(LocalDateTime hour, Long activityId, Long userId,
                             String criptoName, Double valueCripto, Long nominals,
                             Double amountInArs, String username, String lastName,
                             Integer operations, Long reputation) {
        this.hour = hour;
        this.activityId = activityId;
        this.userId = userId;
        this.criptoName = criptoName;
        this.valueCripto = valueCripto;
        this.nominals = nominals;
        this.amountInArs = amountInArs;
        this.username = username;
        this.lastName = lastName;
        this.operations = operations;
        this.reputation = reputation;
    }

    public LocalDateTime getHour() {
        return hour;
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

    public Long getActivityId() {
        return activityId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getNominals(){return nominals;}
}
