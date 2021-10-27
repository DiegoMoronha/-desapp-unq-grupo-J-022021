package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.TransactionBooleanResponseDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserTransactionDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service.CriptoTransactionService;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.wrapper.UserDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Transactions")
public class CriptoTransactionController {

    @Autowired
    CriptoTransactionService transactionService;

    @ApiOperation(value = "", authorizations = { @Authorization(value="JWT") })
    @PostMapping("/api/transaction/start/{idToNegociate}/activity/{actID}")
    public ResponseEntity<UserTransactionDto> startTransaction(@PathVariable Long idToNegociate,
                                                               @PathVariable Long actID){
        UserDetail userDetail= (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserTransactionDto info = transactionService.startTransaction(userDetail.getId(),idToNegociate,actID);
        return ResponseEntity.ok().body(info);
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="JWT") })
    @PutMapping("/api/transaction/cancel")
    public ResponseEntity<String> cancelTransaction(){
        UserDetail userDetail= (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        transactionService.cancelTransaction(userDetail.getId());
        return ResponseEntity.accepted().body("accepted");
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="JWT") })
    @GetMapping("/api/transaction/send/{idToNegociate}")
    public ResponseEntity checkUserCompleteSend(@PathVariable Long idToNegociate) throws Exception {
        try {
            UserDetail userDetail= (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            TransactionBooleanResponseDto response = transactionService.checkUserCompleteSend(userDetail.getId(),idToNegociate);
            return ResponseEntity.ok().body(response);
        }
        catch (Exception e ){
           TransactionBooleanResponseDto resp = new TransactionBooleanResponseDto();
                resp.setErr_msg(e.getMessage());
            return ResponseEntity.badRequest().body(resp);
        }
    }


    @ApiOperation(value = "", authorizations = { @Authorization(value="JWT") })
    @PutMapping("/api/transaction/confirm")
    public ResponseEntity<String> confirmTransaction(){
        UserDetail userDetail= (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        transactionService.confirmTransaction(userDetail.getId());
        return ResponseEntity.accepted().body("accepted");
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="JWT") })
    @GetMapping("/api/transaction/confirm/start")
    public ResponseEntity confirmStartTransaction(){
        UserDetail userDetail= (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TransactionBooleanResponseDto response = transactionService.notifyStartTransaction(userDetail.getId());
        return ResponseEntity.accepted().body(response);
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="JWT") })
    @PostMapping("/api/transaction/confirm/activity/{activityId}/finish/{idToNegociate}")
    public ResponseEntity<String> completeTransaction(@PathVariable Long idToNegociate,
                                                      @PathVariable Long activityId){
        UserDetail userDetail= (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        transactionService.completeTransaction(activityId,userDetail.getId(),idToNegociate);
        return ResponseEntity.accepted().body("completed");
    }

}
