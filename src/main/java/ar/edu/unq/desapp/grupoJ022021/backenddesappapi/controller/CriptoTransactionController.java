package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.TransactionBooleanResponseDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.TransactionDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserTransactionDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service.CriptoTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CriptoTransactionController {

    @Autowired
    CriptoTransactionService transactionService;

    @PostMapping("/api/transaction/start/{idToNegociate}")
    public ResponseEntity<UserTransactionDto> startTransaction(@RequestHeader("Authorization")String token ,
                                                               @PathVariable Long idToNegociate,
                                                               @RequestBody TransactionDto transactionData){
        UserTransactionDto info = transactionService.startTransaction(token,idToNegociate,transactionData);
        return ResponseEntity.ok().body(info);
    }

    @PutMapping("/api/transaction/cancel")
    public ResponseEntity<String> cancelTransaction(@RequestHeader("Authorization") String token){
        transactionService.cancelTransaction(token);
        return ResponseEntity.accepted().body("accepted");
    }

    @GetMapping("/api/transaction/send/{idToNegociate}")
    public ResponseEntity checkUserCompleteSend(@PathVariable Long idToNegociate) throws Exception {
        try {
            TransactionBooleanResponseDto response = transactionService.checkUserCompleteSend(idToNegociate);
            return ResponseEntity.ok().body(response);
        }
        catch (Exception e ){
           TransactionBooleanResponseDto resp = new TransactionBooleanResponseDto();
                resp.setErr_msg(e.getMessage());
            return ResponseEntity.badRequest().body(resp);
        }
    }



    @PutMapping("/api/transaction/confirm")
    public ResponseEntity<String> confirmTransaction(@RequestHeader("Authorization") String token){
        transactionService.confirmTransaction(token);
        return ResponseEntity.accepted().body("accepted");
    }

    @GetMapping("/api/transaction/confirm/start")
    public ResponseEntity confirmStartTransaction(@RequestHeader("Authorization") String token){
        TransactionBooleanResponseDto response = transactionService.notifyStartTransaction(token);
        return ResponseEntity.accepted().body(response);
    }


    @PostMapping("/api/transaction/confirm/finish/{idToNegociate}")
    public ResponseEntity<String> completeTransaction(@RequestHeader("Authorization") String token,
                                                      @PathVariable Long idToNegociate, @RequestBody TransactionDto transaction){
        transactionService.completeTransaction(transaction,token,idToNegociate);
        return ResponseEntity.accepted().body("completed");
    }

}
