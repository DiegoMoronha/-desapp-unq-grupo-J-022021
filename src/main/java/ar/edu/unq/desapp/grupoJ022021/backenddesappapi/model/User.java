package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
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
    
    @Column(unique = true)
    private String addrWallet;
    
    @OneToMany(mappedBy = "user", cascade =CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = CriptoTransaction.class)
    private List<CriptoTransaction> transactions = new ArrayList<CriptoTransaction>();

    public User(){}


    public User(String name, String lastName, String email, String address, String password
                    , String cvu, String addrWallet) throws Exception {
        super();
        this.name = Validator.between(10,30,name,"must contain between 10 and 30 characters");
        this.lastName= Validator.between(10,30,lastName,"must contain between 10 and 30 characters");;
        this.email=Validator.validateEmail(email,"format email is incorrect");
        this.address = Validator.between(0,30,address,"must contain between 8 and 30 characters");
        this.password= password;
        this.cvu= Validator.between(22,22,cvu,"must contain 22 characters");
        this.addrWallet= Validator.between(8,8,addrWallet,"must contain 8 characters");;

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


    public Long getId() {
        return id;
    }

    public List<CriptoTransaction> getTransactions() {
        return transactions;
    }
}