package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto;

public class UserResultDto {
    private String  name;
    private String  lastName;
    private Integer operations;
    private Long reputation;

    public UserResultDto(String name, String lastName, Integer operations, Long reputation) {
        this.name = name;
        this.lastName = lastName;
        this.operations = operations;
        this.reputation = reputation;
    }

    public String getName() {
        return name;
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
