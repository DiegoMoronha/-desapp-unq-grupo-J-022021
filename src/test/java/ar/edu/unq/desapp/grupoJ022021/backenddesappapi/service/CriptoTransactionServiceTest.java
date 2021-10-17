package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.configKeyValue.KeyValueSaver;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.TransactionBooleanResponseDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.TransactionDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserTransactionDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriptoTransactionServiceTest {

    @Autowired
    private CriptoTransactionService transactionService;

    private TransactionDto transaction;

    @Before
    public void setUp() {


    }

    @Test
    public void startTransactionUser1andObtainDataUser2AndNotifyUser2() {
        KeyValueSaver.putKeyValue("hola", 1L);
        KeyValueSaver.putKeyValue("chau", 2L);
        UserTransactionDto res = transactionService.startTransaction("hola", 2L, 2L);
        Assert.assertEquals("TamaraElizabeth", res.getUsername());
        TransactionBooleanResponseDto res1 = transactionService.notifyStartTransaction("chau");
        Assert.assertTrue(res1.getActive());
        boolean userReceived1L = res1.getId() == 1L;
        Assert.assertTrue(userReceived1L);

    }
}
