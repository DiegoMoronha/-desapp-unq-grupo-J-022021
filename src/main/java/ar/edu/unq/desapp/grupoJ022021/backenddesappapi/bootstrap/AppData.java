package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.bootstrap;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.configKeyValue.KeyValueSaver;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.ActivityDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserRegisterDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service.CriptoActivityService;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AppData {

    @Autowired
    UserService userService;

    @Autowired
    CriptoActivityService activityService;

    @Bean
    public void createUsers() throws Exception {
    UserRegisterDto userTest = new UserRegisterDto("TamaraElizabeth","BenitezAlegre","tamarae@gmail.com","262","1234tamara","1234567891234567891234","TOROVACA");
    UserRegisterDto userTest1 = new UserRegisterDto("DiegoHernan","MoronhaNose","diegoh@gmail.com","392","diego1234","9876543210987654321098","VACALORO");
    userService.registerUser(userTest);
    userService.registerUser(userTest1);
    }

    @Bean
    public void createActivities(){
        KeyValueSaver.putKeyValue("hola",1L);
        KeyValueSaver.putKeyValue("chau",2L);
        ActivityDto activityDto = new ActivityDto("BTC","10","10000000","buy");
        ActivityDto activityDto2 = new ActivityDto("BTC","30","900000000","buy");

        activityService.createNewActivity(activityDto,"hola");
        activityService.createNewActivity(activityDto2,"chau");

    }

}

