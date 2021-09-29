package ar.edu.unq.desapp.grupoJ022021.backenddesappapi;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    private String name;
    private String lastName ;
    private String email;
    private String address;
    private String password;
    private String cvu;
    private String addrWallet;


    public User(){}


    public User(String name, String lastName, String email, String address, String password
            , String cvu, String addrWallet){
        super();
        this.name = name;
        this.lastName= lastName;
        this.email= email;
        this.address = address;
        this.password= password;
        this.cvu=cvu;
        this.addrWallet=addrWallet;

    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getCvu() {
        return cvu;
    }

    public String getAddrWallet() {
        return addrWallet;
    }




    public int getId() {
        return id;
    }


}