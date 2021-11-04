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
        activityDto = new ActivityDto("BTC",1.00,10L,1.0000000,"buy");

    }

    @Test
    public void createActivityAndCheckActivitiesByTickerAndType(){
        activityService.createNewActivity(activityDto,1L);
        Assert.assertTrue(activityService.getActivitiesByUser(1L).size()>0);
        List<ActivityResultDto> res = activityService.getActivitiesByTickerAndType(1L,"BTC","buy");
        Assert.assertEquals("BTC",res.get(0).getCriptoName());

    }


    @After
    public void clear(){
    }
}
