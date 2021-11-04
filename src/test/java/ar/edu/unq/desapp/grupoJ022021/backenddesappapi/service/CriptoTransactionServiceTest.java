package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.TransactionBooleanResponseDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserTransactionDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriptoTransactionServiceTest {

    @Autowired
    private CriptoTransactionService transactionService;

    @Before
    public void setUp() {


    }

    @Test
    public void startTransactionUser1andObtainDataUser2AndNotifyUser2() {
        UserTransactionDto res = transactionService.startTransaction(1L, 2L, 2L);
        Assert.assertEquals("TamaraElizabeth", res.getUsername());
        TransactionBooleanResponseDto res1 = transactionService.notifyStartTransaction(2L);
        Assert.assertTrue(res1.getActive());
        boolean userReceived1L = res1.getId() == 1L;
        Assert.assertTrue(userReceived1L);

    }
}
