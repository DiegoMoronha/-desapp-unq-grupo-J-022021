package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.Exceptions.UserAlreadyExistsException;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserRegisterDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    private UserRegisterDto userTest;
    private UserRegisterDto userTest1;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception{
         userTest = new UserRegisterDto("TamaraElizabeth","BenitezAlegre","tamarae@gmail.com","262","1234tamara","1234567891234567891234","TOROVACA");
         userTest1 = new UserRegisterDto("DiegoHernan","MoronhaNose","diegoh@gmail.com","392","diego1234","9876543210987654321098","VACALORO");
    }

    @Test
    public void checkRegisterUser() throws Exception {

        userService.registerUser(userTest);
        Assert.assertTrue(userService.getUsers().size()>0);

    }

    @Test(expected = UserAlreadyExistsException.class)
    public void userExistsException() throws Exception {

        userService.registerUser(userTest);
        userService.registerUser(userTest);
    }

    @Test
    public void isValidUserTest() throws Exception {

        userService.registerUser(userTest);
        Assert.assertTrue(userService.isValidUser("TamaraElizabeth","1234tamara"));
    }

    @After
    public void clear(){
        userService.clearDatabase();
    }
}
