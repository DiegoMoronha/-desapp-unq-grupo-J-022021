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
import java.time.ZoneId;
import java.util.Optional;

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


    public UserTransactionDto startTransaction(Long id,Long userIdToNegociate, Long actId){
        initTransactionHour = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
        CriptoActivity act=activityRepository.findById(actId).get();
        KeyValueSaver.initTransaction(id,userIdToNegociate);
        User user = userRepository.findById(userIdToNegociate);
        if(act.getActivityType().equals("buy")) {

            return new UserTransactionDto(user.getName(),user.getLastName(),act.getCriptoName(),
                    act.getValueCripto(),act.getAmountInArs(),user.getTransactions().size(),
                    user.getReputation(),user.getAddrWallet());
        }
        else{
            return new UserTransactionDto(user.getName(),user.getLastName(),act.getCriptoName(),
                    act.getValueCripto(),act.getAmountInArs(),user.getTransactions().size(),
                    user.getReputation(),user.getCvu());
        }
    }

    private boolean isTransactionInProgress(Long iduser){
        return KeyValueSaver.isCompletedTransaction(iduser) !=null;
    }


    public void cancelTransaction(Long iduser){
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

    public TransactionBooleanResponseDto notifyStartTransaction(Long id){
        Long idToNegociate= KeyValueSaver.getIdToNegociate(id);
        boolean isActive =isTransactionInProgress(id);
        TransactionBooleanResponseDto resp =new TransactionBooleanResponseDto(idToNegociate,isActive);
        return resp;
    }

    public void confirmTransaction(Long id){
        KeyValueSaver.completeTransaction(id);
    }

    public void completeTransaction(Long idActivity ,Long idUser,Long userIdToNegociate){
        confirmTransaction(idUser);
        CriptoActivity activity = activityRepository.findById(idActivity).get();
       if(usersCompleteTransaction(idUser,userIdToNegociate)) {
           User userToNegociate = userRepository.findById(userIdToNegociate);
           User user = userRepository.findById(idUser);

           LocalDateTime finishTransaction = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
          initTransactionHour.plusMinutes(30);
          Long score = generateScore(finishTransaction.isAfter(initTransactionHour));
          CriptoTransaction transaction = new CriptoTransaction(finishTransaction,activity.getActivityType()
                        , activity.getCriptoName(),activity.getValueCripto(),
                        activity.getAmountInArs(), score,user);

            CriptoTransaction transactionUserToNegociate=new CriptoTransaction(finishTransaction,opposite(activity.getActivityType())
                    , activity.getCriptoName(),activity.getValueCripto(),
                    activity.getAmountInArs(), score,userToNegociate);

           saveDataTransaction(user,userToNegociate,score,transaction,transactionUserToNegociate);

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

    private void saveDataTransaction(User user,User userToNegociate,Long score,CriptoTransaction transaction,
                                     CriptoTransaction transactionUserToNegociate){
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

