package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.bootstrap;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.configKeyValue.KeyValueSaver;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.ActivityDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserRegisterDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoActivity;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoTransaction;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.CriptoActivityRepository;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.CriptoTransactionRepository;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.UserRepository;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service.CriptoActivityService;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AppData implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
    createUsersData();
    }


    private void createUsersData() throws Exception {
        User u1 =new User("DiegoHernan","MoronhaUdrizar","diego@gmail.com","392 1235","1234",
                "1234567890123456789012","87654321");
        User u2 =new User("TamaraElizabeth","BenitezAlegre","tamaraelizabethbenitez98@gmail.com","no recuerdo","1234",
                "1234567890123456789013","97654321");
        CriptoTransaction t1=new CriptoTransaction(LocalDateTime.now(),"buy","ETH",1L,"10","10.000,00",10L,u1);
        CriptoTransaction t2= new CriptoTransaction(LocalDateTime.now(),"sell","ETH",1L,"10","10.000,00",10L,u2);
        CriptoActivity a1 = new CriptoActivity(LocalDateTime.now(),"BTC",1l,"10","1.000.000,00","buy",u1);
        CriptoActivity a2 = new CriptoActivity(LocalDateTime.now(),"BTC",10L,"50","100.000.000.000,00","buy",u2);
        CriptoActivity a3 = new CriptoActivity(LocalDateTime.now(),"ADA",10L,"50","100.000.000.000,00","sell",u1);

        u1.setReputation(10L);
        u2.setReputation(10L);
        u1.addTransaction(t1);
        u2.addTransaction(t2);
        u1.addActivity(a1);
        u2.addActivity(a2);
        u1.addActivity(a3);
        userRepository.save(u1);
        userRepository.save(u2);
    }


}

