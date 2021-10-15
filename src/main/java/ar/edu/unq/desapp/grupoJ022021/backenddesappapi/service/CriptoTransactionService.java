package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.configKeyValue.KeyValueSaver;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.TransactionBooleanResponseDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.TransactionDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserTransactionDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoActivity;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoTransaction;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.CriptoActivityRepository;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.CriptoTransactionRepository;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class CriptoTransactionService {

    @Autowired
    private CriptoTransactionRepository criptoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CriptoActivityRepository activityRepository;

    private LocalDateTime initTransactionHour;


    public UserTransactionDto startTransaction(String token,Long userIdToNegociate, TransactionDto transactionData){
        initTransactionHour = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
        Long id = KeyValueSaver.getUserIdLogged(token);
        KeyValueSaver.initTransaction(id,userIdToNegociate);
        User user = userRepository.findById(userIdToNegociate);
        if(transactionData.getTransactionType().equals("buy")) {

            return new UserTransactionDto(user.getName(),user.getLastName(),transactionData.getCriptoName(),
                    transactionData.getCriptoAmount(),transactionData.getAmountInArs(),user.getTransactions().size(),
                    user.getReputation(),user.getAddrWallet());
        }
        else{
            return new UserTransactionDto(user.getName(),user.getLastName(),transactionData.getCriptoName(),
                    transactionData.getCriptoAmount(),transactionData.getAmountInArs(),user.getTransactions().size(),
                    user.getReputation(),user.getCvu());
        }
    }

    private boolean isTransactionInProgress(String token){
        Long iduser = KeyValueSaver.getUserIdLogged(token);
        if( KeyValueSaver.isCompletedTransaction(iduser)==null){
            return false;
        }
        else{
            return true;
        }
    }


    public void cancelTransaction(String token){
        Long iduser = KeyValueSaver.getUserIdLogged(token);
        User user =  userRepository.findById(iduser);
        user.discountReputation(20L);
        KeyValueSaver.cancelTransaction(iduser);
        userRepository.save(user);
    }

    public TransactionBooleanResponseDto checkUserCompleteSend(Long userID) throws Exception {
        if(KeyValueSaver.isCompletedTransaction(userID) == null){
            throw new Exception("the transaction was canceled");
        }
        else {
           boolean active =KeyValueSaver.isCompletedTransaction(userID);
           TransactionBooleanResponseDto resp =new TransactionBooleanResponseDto(userID,active);
            return resp;
        }
    }

    public TransactionBooleanResponseDto notifyStartTransaction(String token){
        Long id = KeyValueSaver.getUserIdLogged(token);
        Long idToNegociate= KeyValueSaver.getIdToNegociate(id);
        boolean isActive =isTransactionInProgress(token);
        TransactionBooleanResponseDto resp =new TransactionBooleanResponseDto(idToNegociate,isActive);
        return resp;
    }

    public void confirmTransaction(String token){
        Long id = KeyValueSaver.getUserIdLogged(token);
        KeyValueSaver.completeTransaction(id);
    }

    public void completeTransaction(Long idActivity ,String token,Long userIdToNegociate){
        confirmTransaction(token);
        CriptoActivity activity = activityRepository.findById(idActivity).get();
       Long id = KeyValueSaver.getUserIdLogged(token);
       if(usersCompleteTransaction(id,userIdToNegociate)) {

          LocalDateTime finishTransaction = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
          initTransactionHour.plusMinutes(30);
          Long score = generateScore(finishTransaction.isAfter(initTransactionHour));
          CriptoTransaction transaction = new CriptoTransaction(finishTransaction,activity.getActivityType()
                        , activity.getCriptoName(),activity.getValueCripto(),
                        activity.getAmountInArs(), score);

            CriptoTransaction transactionUserToNegociate=new CriptoTransaction(finishTransaction,opposite(activity.getActivityType())
                    , activity.getCriptoName(),activity.getValueCripto(),
                    activity.getAmountInArs(), score);

           saveDataTransaction(id,userIdToNegociate,score,transaction,transactionUserToNegociate);

       }
        }

        private String  opposite(String transactionType){
        if(transactionType.equals("buy")){
            return "sell";
        }
        else{
            return "buy";
        }
        }

        private Long generateScore(boolean condition){
        if(condition){
            return 10L;
        }
        else{
            return 5L;
    }
}

    private boolean usersCompleteTransaction(Long id,Long idToNegociate){
        boolean isCompleteUserTransaction =KeyValueSaver.isCompletedTransaction(id);
        boolean isCompleteUserToNegociate =KeyValueSaver.isCompletedTransaction(idToNegociate);
        return   isCompleteUserTransaction && isCompleteUserToNegociate;
    }

    private void saveDataTransaction(Long id, Long userIdToNegociate,Long score,CriptoTransaction transaction,
                                     CriptoTransaction transactionUserToNegociate){
        User userToNegociate = userRepository.findById(userIdToNegociate);
        User user = userRepository.findById(id);
        user.addTransaction(transaction);
        userToNegociate.addTransaction(transactionUserToNegociate);
        user.sumReputation(score);
        userToNegociate.sumReputation(score);
        Long reputationUser =user.calculateReputation();
        Long reputationUserToNegociate = user.calculateReputation();
        user.setReputation(reputationUser);
        user.setReputation(reputationUserToNegociate);
        userRepository.save(user);
        userRepository.save(userToNegociate);

    }

    public void clearDatabase(){
        criptoService.deleteAllInBatch();
    }
}

