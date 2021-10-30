package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.aspects.ExceptionAspect;
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

    @ApiOperation(value = "start transaction", authorizations = { @Authorization(value="JWT") })
    @ExceptionAspect
    @PostMapping(value="/api/transaction/start/{idToNegociate}/activity/{actID}",produces="application/json")
    public ResponseEntity<UserTransactionDto> startTransaction(@PathVariable Long idToNegociate,
                                                               @PathVariable Long actID){
        UserDetail userDetail= (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserTransactionDto info = transactionService.startTransaction(userDetail.getId(),idToNegociate,actID);
        return ResponseEntity.ok().body(info);
    }

    @ApiOperation(value = "cancel transaction", authorizations = { @Authorization(value="JWT") })
    @ExceptionAspect
    @PutMapping(value="/api/transaction/cancel", produces="application/json")
    public ResponseEntity<String> cancelTransaction(){
        UserDetail userDetail= (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        transactionService.cancelTransaction(userDetail.getId());
        return ResponseEntity.accepted().body("accepted");
    }

    @ApiOperation(value = "check if user complete send", authorizations = { @Authorization(value="JWT") })
    @ExceptionAspect
    @GetMapping(value="/api/transaction/send/{idToNegociate}",produces="applicaton/json")
    public ResponseEntity checkUserCompleteSend(@PathVariable Long idToNegociate) throws Exception {
            UserDetail userDetail= (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            TransactionBooleanResponseDto response = transactionService.checkUserCompleteSend(userDetail.getId(),idToNegociate);
            return ResponseEntity.ok().body(response);
    }


    @ApiOperation(value = "confirm transaction", authorizations = { @Authorization(value="JWT") })
    @ExceptionAspect
    @PutMapping(value="/api/transaction/confirm",produces="application/json")
    public ResponseEntity<String> confirmTransaction(){
        UserDetail userDetail= (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        transactionService.confirmTransaction(userDetail.getId());
        return ResponseEntity.accepted().body("accepted");
    }

    @ApiOperation(value = "check init transaction", authorizations = { @Authorization(value="JWT") })
    @ExceptionAspect
    @GetMapping(value="/api/transaction/confirm/start",produces="application/json")
    public ResponseEntity confirmStartTransaction(){
        UserDetail userDetail= (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TransactionBooleanResponseDto response = transactionService.notifyStartTransaction(userDetail.getId());
        return ResponseEntity.accepted().body(response);
    }

    @ApiOperation(value = "complete transaction", authorizations = { @Authorization(value="JWT") })
    @ExceptionAspect
    @PostMapping(value="/api/transaction/confirm/activity/{activityId}/finish/{idToNegociate}",produces="application/json")
    public ResponseEntity<String> completeTransaction(@PathVariable Long idToNegociate,
                                                      @PathVariable Long activityId){
        UserDetail userDetail= (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        transactionService.completeTransaction(activityId,userDetail.getId(),idToNegociate);
        return ResponseEntity.accepted().body("completed");
    }

    @ApiOperation(value = "check user transaction in progress", authorizations = { @Authorization(value="JWT") })
    @ExceptionAspect
    @GetMapping(value="/api/transaction/inProgress/{iduser}",produces="application/json")
    public ResponseEntity<TransactionBooleanResponseDto> transactionUserInProgress(@PathVariable Long idUser){
       TransactionBooleanResponseDto response= transactionService.userIsInTransaction(idUser);
        return ResponseEntity.ok(response);
    }


}
