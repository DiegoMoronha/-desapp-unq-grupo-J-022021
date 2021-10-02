package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model;

import javax.persistence.*;

@Entity
public class CriptoActivity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String hour;
   private String criptoName;
   private Double valueCripto;
   private Double amountInArs;

   @ManyToOne
   private User user;

}
