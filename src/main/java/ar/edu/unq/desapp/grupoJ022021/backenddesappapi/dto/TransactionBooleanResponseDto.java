package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto;

public class TransactionBooleanResponseDto {
    private Long id = null;
    private Boolean isActive;
    private String err_msg = null ;

    public TransactionBooleanResponseDto(Long id, Boolean isActive) {
        this.id = id;
        this.isActive = isActive;
    }

    public TransactionBooleanResponseDto() {
    }


    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public Long getId() {
        return id;
    }


    public Boolean getActive() {
        return isActive;
    }

    public String getErr_msg() {
        return err_msg;
    }

}
