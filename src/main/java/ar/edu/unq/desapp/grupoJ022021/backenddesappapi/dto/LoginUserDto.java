package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto;

public class LoginUserDto {
    private String username ;
    private String password ;

    public LoginUserDto(String email, String password) {
        this.username = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
