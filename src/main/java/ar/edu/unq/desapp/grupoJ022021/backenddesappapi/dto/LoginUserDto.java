package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto;

public class LoginUserDto {
    private String email ;
    private String password ;

    public LoginUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
