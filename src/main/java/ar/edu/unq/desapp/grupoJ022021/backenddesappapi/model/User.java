package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.exceptions.ValidationException;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName ;
    private String email;
    private String address;
    private String password;
    private String cvu;
    private Long reputation=0L;
    private String addrWallet;
    
    @OneToMany(mappedBy = "user", cascade =CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = CriptoTransaction.class)
    private List<CriptoTransaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade =CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = CriptoActivity.class)
    private List<CriptoActivity> activities = new ArrayList<>();

    public User(){}


    public User(String name, String lastName, String email, String address, String password
                    , String cvu, String addrWallet) throws ValidationException {
        super();
        this.name = Validator.between(10,30,name,"name must contain between 10 and 30 characters");
        this.lastName= Validator.between(10,30,lastName,"lastName must contain between 10 and 30 characters");
        this.email=Validator.validateEmail(email,"format email is incorrect");
        this.address = Validator.between(0,30,address,"address must contain between 0 and 30 characters");
        this.password= password;
        this.cvu= Validator.between(22,22,cvu,"cvu must contain 22 characters");
        this.addrWallet= Validator.between(8,8,addrWallet,"Address Wallet must contain 8 characters");

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

    public void addTransaction(CriptoTransaction ct){
        transactions.add(ct);
    }

    public void addActivity(CriptoActivity act){
        activities.add(act);
    }

    public Long getId() {
        return id;
    }

    public List<CriptoTransaction> getTransactions() {
        return transactions;
    }

    public List<CriptoActivity> getActivities() {
        return activities;
    }

    public void setReputation(Long rep){
        reputation=rep;
    }

    public Long getReputation() {
        return reputation;
    }

    public void sumReputation(Long rep) {
        this.reputation += rep;
    }

    public void discountReputation(Long rep){
        this.reputation -= rep;
    }

    public Long calculateReputation(){
       Integer operations=transactions.size();
       if(operations>0){
           return reputation / operations ;
       }
        return reputation;
    }

    public Integer getOperations(){
        return transactions.size();
    }
}