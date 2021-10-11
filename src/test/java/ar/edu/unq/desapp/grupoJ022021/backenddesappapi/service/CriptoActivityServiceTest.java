package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.configKeyValue.KeyValueSaver;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.ActivityDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.ActivityResultDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserRegisterDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriptoActivityServiceTest {

    @Autowired
    CriptoActivityService activityService;

    @Autowired
    private UserService userService;


    private ActivityDto activityDto;
    private UserRegisterDto userTest1;

    @Before
    public void setUp() throws Exception{
        KeyValueSaver.putKeyValue("hola",1L);
        activityDto = new ActivityDto("BTC","10","10000000","buy");
        userTest1 = new UserRegisterDto("DiegoHernan","MoronhaNose","diegoh@gmail.com","392","diego1234","9876543210987654321098","VACALORO");
        userService.registerUser(userTest1);
    }

    @Test
    public void createActivityAndCheckActivitiesByTickerAndType(){
        activityService.createNewActivity(activityDto,"hola");
        Assert.assertTrue(activityService.getActivitiesByUser("hola").size()>0);
        List<ActivityResultDto> res = activityService.getActivitiesByTickerAndType("BTC","buy");
        Assert.assertEquals(res.get(0).getCriptoName(),"BTC");

    }


    @After
    public void clear(){
    KeyValueSaver.removeLogguedUser("hola");
    activityService.clearDatabase();
    userService.clearDatabase();
    }
}
