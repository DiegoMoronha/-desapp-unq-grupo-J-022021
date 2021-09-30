package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.wrapper;

import org.springframework.security.core.userdetails.User;
import java.util.Collections;


public class UserDetail extends User {
    private Long id;

    public UserDetail(String userName, String password, Long id){
        super(userName,password,Collections.emptyList());
        this.id = id;
    }

    public Long getId(){
        return id;
    }



}
